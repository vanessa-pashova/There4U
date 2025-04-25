package com.example.there4u.model.user;

import lombok.Getter;

@Getter
public class RegularUser extends User {
    private String UCN; //Unique Civil Number

    public RegularUser(String name, String email, String password, String phoneNumber, String address, TypeOfUser typeOfUser, String UCN ) {
        super(name, email, password, phoneNumber, address, typeOfUser);
        this.setUCN(UCN);
    }

    public void setUCN(String UCN) {
        if(UCN.length() != 10) {
            throw new IllegalArgumentException(">! UCN length must be exactly 10 digits, [setUCN(), RegularUser]");
        }

        this.UCN = UCN;
    }
}
