package com.example.VaraBari.Objects;

import java.util.ArrayList;
import java.util.Comparator;

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

    public static Comparator<House> compareByHouseRentAsc = new Comparator<House>() {
        @Override
        public int compare(House o1, House o2) {
            Integer a = new Integer(o1.rent);
            Integer b = new Integer(o2.rent);
            return a.compareTo(b);
        }
    };
    public static Comparator<House> compareByHouseRentDsc = new Comparator<House>() {
        @Override
        public int compare(House o1, House o2) {
            Integer a = new Integer(o1.rent);
            Integer b = new Integer(o2.rent);
            return b.compareTo(a);
        }
    };

    public static Comparator<House> compareByHouseAreaAsc = new Comparator<House>() {
        @Override
        public int compare(House o1, House o2) {
            Double area1 = new Double(o1.area);
            Double area2 = new Double(o2.area);
            return area1.compareTo(area2);
        }
    };
    public static Comparator<House> compareByHouseAreaDsc = new Comparator<House>() {
        @Override
        public int compare(House o1, House o2) {
            Double area1 = new Double(o1.area);
            Double area2 = new Double(o2.area);
            return area2.compareTo(area1);
        }
    };

    public static Comparator<House> compareByHouseBedroomAsc = new Comparator<House>() {
        @Override
        public int compare(House o1, House o2) {
            Integer a = new Integer(o1.bedRoom);
            Integer b = new Integer(o2.bedRoom);
            return a.compareTo(b);
        }
    };
    public static Comparator<House> compareByHouseBedroomDsc = new Comparator<House>() {
        @Override
        public int compare(House o1, House o2) {
            Integer a = new Integer(o1.bedRoom);
            Integer b = new Integer(o2.bedRoom);
            return b.compareTo(a);
        }
    };

    public static Comparator<House> compareByHouseAttachBathAsc = new Comparator<House>() {
        @Override
        public int compare(House o1, House o2) {
            Integer a = new Integer(o1.attachBath);
            Integer b = new Integer(o2.attachBath);
            return a.compareTo(b);
        }
    };
    public static Comparator<House> compareByHouseAttachBathDsc = new Comparator<House>() {
        @Override
        public int compare(House o1, House o2) {
            Integer a = new Integer(o1.attachBath);
            Integer b = new Integer(o2.attachBath);
            return b.compareTo(a);
        }
    };
}
