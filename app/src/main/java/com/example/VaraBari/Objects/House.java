package com.example.VaraBari.Objects;

public class House {
    public String image1, image2, image3, image4, image5;

    public String title, description, address;

    public double area;
    public int bedRoom, attachBath, belcony;
    public boolean drawingRoomAvailable, diningRoomAvailable, storeRoomAvailable;
    public int floorLevel, rent;
    public String availableFrom;
    public boolean negotiable;

    public String email, phoneNo;

    public House() {
        image1 = "";
        image2 = "";
        image3 = "";
        image4 = "";
        image5 = "";
        title = "";
        description = "";
        address = "";
        area = 0.0;
        bedRoom = 0;
        attachBath = 0;
        belcony = 0;
        drawingRoomAvailable = false;
        diningRoomAvailable = false;
        storeRoomAvailable = false;
        floorLevel = 0;
        rent = 0;
        availableFrom = "";
        negotiable = false;
        email = "";
        phoneNo = "";
    }
}
