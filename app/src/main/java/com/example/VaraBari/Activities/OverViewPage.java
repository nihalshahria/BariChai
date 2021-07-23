package com.example.VaraBari.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.VaraBari.Objects.House;
import com.example.VaraBari.R;

public class OverViewPage extends AppCompatActivity {

    public TextView area, bedRoom, drawingRoom, diningRoom, storeRoom, attachBath, balcony, floorlvl;
    public TextView rent, negotiable, availableFrom, description, address, phoneNo, email, title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_page);

        Intent intent = getIntent();
        House house = intent.getParcelableExtra("House");

        title = findViewById(R.id.overview_title);
        title.setText(house.title);

        area = findViewById(R.id.overview_area);
        area.setText(String.valueOf(house.area));

        bedRoom = findViewById(R.id.overview_bed_room);
        bedRoom.setText(String.valueOf(house.bedRoom));

        drawingRoom = findViewById(R.id.overview_drawing_room);
        if(house.drawingRoomAvailable){
            drawingRoom.setText("Yes");
        }else{
            drawingRoom.setText("No");
        }

        diningRoom = findViewById(R.id.overview_dining_room);
        if(house.diningRoomAvailable){
            drawingRoom.setText("Yes");
        }else{
            drawingRoom.setText("No");
        }

        storeRoom = findViewById(R.id.overview_store_room);
        if(house.storeRoomAvailable){
            drawingRoom.setText("Yes");
        }else{
            drawingRoom.setText("No");
        }

        attachBath = findViewById(R.id.overview_attach_bath);
        attachBath.setText(String.valueOf(house.attachBath));

        balcony = findViewById(R.id.overview_balcony);
        balcony.setText(String.valueOf(house.balcony));

        floorlvl = findViewById(R.id.overview_floor_level);
        floorlvl.setText(house.floorLevel);

        rent = findViewById(R.id.overview_rent);
        rent.setText(String.valueOf(house.rent) + " BDT");

        negotiable = findViewById(R.id.overview_negotiable);
        if(house.negotiable){
            negotiable.setText("Yes");
        }else{
            negotiable.setText("No");
        }

        availableFrom = findViewById(R.id.overview_available);
        availableFrom.setText(house.availableFrom);

        description = findViewById(R.id.overview_description);
        description.setText(house.description);

        address = findViewById(R.id.overview_address);
        address.setText(house.address);

        phoneNo = findViewById(R.id.overview_phone_no);
        phoneNo.setText(house.phoneNo);

        email = findViewById(R.id.overview_email);
        email.setText(house.email);

    }
}