package com.example.there4u.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
@Table(name = "regular_user")
public class RegularUser extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Pattern(regexp = "^[0-9]{10}", message = "!> Incorect Civil Number. Civil Number must be exactly 10 digits")
    @Column(name = "UCN")
    private String UCN; //Unique Civil Number

    public RegularUser(String username, String name, String email, String password, String phoneNumber, String address,  String UCN ) {
        super(username, name, email, password, phoneNumber, address, TypeOfUser.REGULAR_USER);
        this.UCN = UCN;
    }

    public void setUCN(String UCN) {
        if(UCN.length() != 10) {
            throw new IllegalArgumentException(">! UCN length must be exactly 10 digits, [setUCN(), RegularUser]");
        }

        this.UCN = UCN;
    }
}
