package com.example.VaraBari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class EditUserInfo extends AppCompatActivity {

    public EditText editPhoneNo, editAddress, editfullName;
    public ImageView editProfileImage;
    private String _fullName, _phoneNo, _address;
    private String uuid;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        myRef = FirebaseDatabase.getInstance().getReference("Users");
        uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Hooks
        editProfileImage = (ImageView)findViewById(R.id.edit_profile_pic);
        editfullName = (EditText)findViewById(R.id.edit_full_name);
        editPhoneNo = (EditText)findViewById(R.id.edit_user_phone_no);
        editAddress = (EditText)findViewById(R.id.edit_user_address);


        //        showPreviousData();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                _fullName = dataSnapshot.child(uuid).child("fullName").getValue(String.class);
                _phoneNo = dataSnapshot.child(uuid).child("phoneNo").getValue(String.class);
                _address = dataSnapshot.child(uuid).child("address").getValue(String.class);

                if(!_fullName.isEmpty())editfullName.setText(_fullName);
                if(!_phoneNo.isEmpty())editPhoneNo.setText(_phoneNo);
                if(!_address.isEmpty())editAddress.setText(_address);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(EditUserInfo.this, "Error!", Toast.LENGTH_SHORT).show();
            }

        });

    }

    public void saveUpdatedInfo(View view) {
        if(isChanged(editfullName, _fullName, "fullName") || isChanged(editPhoneNo, _phoneNo, "phoneNo") || isChanged(editAddress, _address, "address")){
            Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No change detected", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isChanged(EditText Field, String previousData, String childName){
        if(!previousData.equals(Field.getText().toString())){
            myRef.child(uuid).child(childName).setValue(Field.getText().toString());
            return true;
        }else{
            return false;
        }

    }
}