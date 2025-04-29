package com.example.there4u.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "regular_user")
public class RegularUser extends User {
    private static long userId = 94000;

    @Pattern(regexp = "^[0-9]{10}", message = "!> Incorrect Civil Number. Civil Number must be exactly 10 digits")
    @Column(name = "ucn")
    private String UCN; //Unique Civil Number

    @Override
    protected long generateId() {
        if(userId >= 100000)
        {
            throw new IllegalArgumentException("The database has enough users");
        }
        return userId++;
    }

    public RegularUser(String username, String name, String email, String password, String phoneNumber, String address, String UCN) {
        super(username, name, email, password, phoneNumber, address);
        this.UCN = UCN;
        this.setTypeOfUser(typeOfUser);
    }

    public void setTypeOfUser(TypeOfUser typeOfUser) {
        this.typeOfUser = TypeOfUser.REGULAR_USER;
    }
}
