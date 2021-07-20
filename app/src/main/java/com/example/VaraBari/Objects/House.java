package com.example.VaraBari.Objects;

import java.util.ArrayList;

public class House {
    public ArrayList<String>image = new ArrayList<String>();

    public String title, description, address;

    public double area;
    public int bedRoom, attachBath, balcony;
    public boolean drawingRoomAvailable, diningRoomAvailable, storeRoomAvailable;
    public int  rent;
    public String availableFrom, floorLevel;
    public boolean negotiable, isAvailable;

    public String email, phoneNo;

    public House() {
        title = "";
        description = "";
        address = "";
        floorLevel = "";
        availableFrom = "";
        email = "";
        phoneNo = "";
        bedRoom = 0;
        attachBath = 0;
        balcony = 0;
        rent = 0;
        area = 0.0;
        drawingRoomAvailable = false;
        diningRoomAvailable = false;
        storeRoomAvailable = false;
        isAvailable = true;
        negotiable = false;
    }

    public House(ArrayList<String> image, String title, String description, String address, double area, int bedRoom, int attachBath, int balcony, boolean drawingRoomAvailable, boolean diningRoomAvailable, boolean storeRoomAvailable, int rent, String availableFrom, String floorLevel, boolean negotiable, boolean isAvailable, String email, String phoneNo) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.address = address;
        this.area = area;
        this.bedRoom = bedRoom;
        this.attachBath = attachBath;
        this.balcony = balcony;
        this.drawingRoomAvailable = drawingRoomAvailable;
        this.diningRoomAvailable = diningRoomAvailable;
        this.storeRoomAvailable = storeRoomAvailable;
        this.rent = rent;
        this.availableFrom = availableFrom;
        this.floorLevel = floorLevel;
        this.negotiable = negotiable;
        this.isAvailable = isAvailable;
        this.email = email;
        this.phoneNo = phoneNo;
    }
}
