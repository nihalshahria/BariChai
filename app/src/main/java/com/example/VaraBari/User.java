package com.example.VaraBari;

public class User {
    public String fullName, userName, email, phoneNo, address, profileImagePath;
    public User(){
        this.fullName = "";
        this.userName = "";
        this.email = "";
        this.phoneNo = "";
        this.address = "";
        this.profileImagePath = "";
    }
    public User(String fullName, String userName, String email){
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.phoneNo = "";
        this.address = "";
        this.profileImagePath = "";
    }
}
