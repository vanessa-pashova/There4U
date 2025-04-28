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

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull(message = ">! NGOid cannot be null")
    @Pattern(regexp = "^(100)[0-9]{3}$", message = ">! NGOid must start with 100 and must be 6 digits")
    @Column(name = "ngo_id")
    private String NGOid;

    @Column(name = "description")
    private String description = "[No description added]";

    public NGOUser(String username, String name, String email, String password, String phoneNumber, String address, String NGOid, String description) {
        super(username, name, email, password, phoneNumber, address);
        this.NGOid = NGOid;
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
