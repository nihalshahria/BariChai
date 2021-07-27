package com.example.VaraBari.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.VaraBari.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class EditUserInfo extends AppCompatActivity {

    // Views
    private EditText editPhoneNo, editAddress, editFullName;
    private ImageView editProfileImage;
    private ProgressBar progressBar;
    private Toolbar toolbar;

    private String _fullName, _phoneNo, _address, _profileImageLink;
    private String uuid;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private Uri uri;

    // Firebase
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private StorageTask imageUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        storageReference = FirebaseStorage.getInstance().getReference("Profile_Images");
        uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Hooks
        editProfileImage = (ImageView)findViewById(R.id.edit_profile_pic);
        editFullName = (EditText)findViewById(R.id.edit_full_name);
        editPhoneNo = (EditText)findViewById(R.id.edit_user_phone_no);
        editAddress = (EditText)findViewById(R.id.edit_user_address);
        progressBar = (ProgressBar)findViewById(R.id.edit_info_progressbar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //        showPreviousData();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                _fullName = dataSnapshot.child(uuid).child("fullName").getValue(String.class);
                _phoneNo = dataSnapshot.child(uuid).child("phoneNo").getValue(String.class);
                _address = dataSnapshot.child(uuid).child("address").getValue(String.class);
                _profileImageLink = dataSnapshot.child(uuid).child("profileImageLink").getValue(String.class);

                if(!_profileImageLink.isEmpty()){
                    Picasso.get().load(_profileImageLink).into(editProfileImage);
                }
                if(!_fullName.isEmpty()){
                    editFullName.setText(_fullName);
                }
                if(!_phoneNo.isEmpty()){
                    editPhoneNo.setText(_phoneNo);
                }
                if(!_address.isEmpty()){
                    editAddress.setText(_address);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(EditUserInfo.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }

        });

        editProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(editProfileImage);
        }
    }

    public void saveUpdatedInfo(View view) {
        if(imageUploadTask != null && imageUploadTask.isInProgress()){
            Toast.makeText(this, "Upload in progress", Toast.LENGTH_SHORT).show();
        }else{
            isImageChanged();
            isChanged(editFullName, _fullName, "fullName");
            isChanged(editPhoneNo, _phoneNo, "phoneNo");
            isChanged(editAddress, _address, "address");
            Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), UserProfile.class));
            finish();
        }

    }

    private boolean isChanged(EditText Field, String previousData, String childName){
        if(!previousData.equals(Field.getText().toString())){
            databaseReference.child(uuid).child(childName).setValue(Field.getText().toString().trim());
            return true;
        }else{
            return false;
        }

    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private boolean isImageChanged(){
        if(imageUri != null){
            StorageReference fileReference = storageReference.child(uuid + "."
            + getFileExtension(imageUri));
            imageUploadTask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 500);
                            if(taskSnapshot.getMetadata() != null){
                                if(taskSnapshot.getMetadata().getReference() != null){
                                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String updatedUrl = uri.toString();
                                            databaseReference.child(uuid).child("profileImageLink").setValue(updatedUrl);
                                        }
                                    });
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(EditUserInfo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressBar.setProgress((int) progress);
                }
            });
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}