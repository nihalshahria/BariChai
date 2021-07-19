package com.example.VaraBari.Objects;

import java.util.ArrayList;

public class House {
//    public String[] image = new String[5];
    public ArrayList<String>image = new ArrayList<String>();

    public String title, description, address;

    public double area;
    public int bedRoom, attachBath, balcony;
    public boolean drawingRoomAvailable, diningRoomAvailable, storeRoomAvailable;
    public int  rent;
    public String availableFrom, floorLevel;
    public boolean negotiable;

    public String email, phoneNo;

    public House() {
        title = "";
        description = "";
        address = "";
        area = 0.0;
        bedRoom = 0;
        attachBath = 0;
        balcony = 0;
        drawingRoomAvailable = false;
        diningRoomAvailable = false;
        storeRoomAvailable = false;
        floorLevel = "";
        rent = 0;
        availableFrom = "";
        negotiable = false;
        email = "";
        phoneNo = "";
    }

}
