package com.example.there4u.model.user;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class RegularUser extends User {
    @Pattern(regexp = "^[0-9]{10}", message = "!> Incorect Civil Number. Civil Number must be exactly 10 digits")
    private String UCN; //Unique Civil Number

    public RegularUser(String username, String name, String email, String password, String phoneNumber, String address, TypeOfUser typeOfUser, String UCN ) {
        super(username, name, email, password, phoneNumber, address, typeOfUser);
        this.UCN = UCN;
    }

    public void setUCN(String UCN) {
        if(UCN.length() != 10) {
            throw new IllegalArgumentException(">! UCN length must be exactly 10 digits, [setUCN(), RegularUser]");
        }

        this.UCN = UCN;
    }
}
