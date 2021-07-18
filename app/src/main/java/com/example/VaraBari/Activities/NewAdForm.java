
package com.example.VaraBari.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.VaraBari.Objects.House;
import com.example.VaraBari.R;

import java.util.Calendar;

public class NewAdForm extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5;
    public EditText title, description, address, area, bedroom, attachBath, floorLvl;
    public  EditText availableFrom, rent, email, phoneNo;
    public CheckBox drawingRoom, diningRoom, storeRoom, negotiable;
    public CardView submit;

//    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ad_form);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Hooks
        imageView1 = (ImageView) findViewById(R.id.new_ad_form_house_image1);
        imageView2 = (ImageView) findViewById(R.id.new_ad_form_house_image2);
        imageView3 = (ImageView) findViewById(R.id.new_ad_form_house_image3);
        imageView4 = (ImageView) findViewById(R.id.new_ad_form_house_image4);
        imageView5 = (ImageView) findViewById(R.id.new_ad_form_house_image5);

        title = (EditText) findViewById(R.id.new_ad_form_title);
        description = (EditText) findViewById(R.id.new_ad_form_imageDescription);
        address = (EditText) findViewById(R.id.new_ad_form_address);
        area = (EditText) findViewById(R.id.new_ad_form_area);
        bedroom = (EditText) findViewById(R.id.new_ad_form_bed_room);
        attachBath = (EditText) findViewById(R.id.new_ad_form_attach_bath);
        floorLvl = (EditText) findViewById(R.id.new_ad_form_floor_level);
        availableFrom = (EditText) findViewById(R.id.new_ad_form_available_date);
        rent = (EditText) findViewById(R.id.new_ad_form_rent);
        email = (EditText) findViewById(R.id.new_ad_form_email);
        phoneNo = (EditText) findViewById(R.id.new_ad_form_phone_no);

        drawingRoom = (CheckBox) findViewById(R.id.new_ad_form_drawing_room);
        diningRoom = (CheckBox) findViewById(R.id.new_ad_form_dining_room);
        storeRoom = (CheckBox) findViewById(R.id.new_ad_form_store_room);
        negotiable = (CheckBox) findViewById(R.id.new_ad_form_negotiate);

        submit = (CardView) findViewById(R.id.new_ad_form_submit);
        ////////////////////////////////////////////////////////////////

        // Datepicker
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        availableFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        NewAdForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        availableFrom.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        ////////////////////////////////////////////////////////////////

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishNewAd();
            }
        });
    }

    private void publishNewAd() {
        House house = new House();
        house.title = title.getText().toString();
        house.description = description.getText().toString();
        house.address = address.getText().toString();
        house.email = email.getText().toString();
        house.phoneNo = phoneNo.getText().toString();
        house.area = Double.parseDouble(area.getText().toString());
        house.bedRoom = Integer.parseInt(bedroom.getText().toString());
        house.attachBath = Integer.parseInt(attachBath.getText().toString());
        house.floorLevel = Integer.parseInt(floorLvl.getText().toString());
        house.rent = Integer.parseInt(rent.getText().toString());
        house.drawingRoomAvailable = drawingRoom.isChecked();
        house.diningRoomAvailable = diningRoom.isChecked();
        house.storeRoomAvailable = storeRoom.isChecked();
        house.negotiable = negotiable.isChecked();
        house.availableFrom = availableFrom.getText().toString();
    }
}