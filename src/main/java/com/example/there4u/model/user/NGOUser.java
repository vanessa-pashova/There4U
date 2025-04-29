package com.example.there4u.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ngo_user")
public class NGOUser extends User {

    private static long NGOid = 100000;

    @Column(name = "description")
    private String description = "[No description added]";

    @Override
    protected long generateId() {
        if(NGOid >= 101000)
        {
            throw new IllegalArgumentException("The database has enough NGO users");
        }
        return NGOid++;
    }

    public NGOUser(String username, String name, String email, String password, String phoneNumber, String address, String description) {
        super(username, name, email, password, phoneNumber, address);
        this.id = generateId();
        this.setDescription(description);
        this.typeOfUser = TypeOfUser.NGO;
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
