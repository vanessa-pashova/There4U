package com.example.there4u.model.user;

import com.example.there4u.model.validators.NGOidValidator;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
public class NGOUser extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long NGOid;

    private String description;

    public NGOUser(String username, String name, String email, String password, String phoneNumber, String address, TypeOfUser typeOfUser, String description) {
        super(username, name, email, password, phoneNumber, address, typeOfUser);
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
