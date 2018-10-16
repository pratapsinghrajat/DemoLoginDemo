package com.example.rajatpratapsingh.simpledblogin;

public class UserInformation {

    public String name;
    public String address;
    public String dateBirth;
    public String contact;
    public String idProof;

    public UserInformation(){

    }

    public UserInformation(String name, String address, String dateBirth, String contact, String idProof) {
        this.name = name;
        this.address = address;
        this.dateBirth = dateBirth;
        this.contact = contact;
        this.idProof = idProof;
    }
}
