package com.example.there4u.model.user;

import com.example.there4u.model.validators.NGOidValidator;
import lombok.Getter;

@Getter
public class NGOUser extends User {
    private long NGOid;
    private String description;

    public NGOUser(String name, String email, String password, String phoneNumber, String address, TypeOfUser typeOfUser, long NGOid, String description) {
        super(name, email, password, phoneNumber, address, typeOfUser);
        this.setNGOid(NGOid);
        this.setDescription(description);
    }

    public void setNGOid(long NGOid) {
        NGOidValidator.validateNGOid(NGOid);
        this.NGOid = NGOid;
    }

    public void setDescription(String description) {
        if(description == null || description.isEmpty()) {
            this.description = "[No description provided]";
        }

        else {
            this.description = description;
        }
    }
}
