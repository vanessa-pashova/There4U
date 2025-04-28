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
@Table(name = "contributor")
public class Contributor extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull(message = ">! ContributorID cannot be null")
    @Pattern(regexp = "^(91|92|93)[0-9]{3}$", message = "Contributor ID must start with 91, 92 or 93 and have exactly 5 digits.")
    @Column(name = "contributor_id")
    private String ContributorID;

    @Column(name = "description")
    private String description = "[No description added]";

    private boolean isValidTypeOfUser(TypeOfUser typeOfUser, String ContributorID) {
        int code = Integer.parseInt(ContributorID.substring(0, 2));
        switch(code) {
            case 91: {
                if(typeOfUser == TypeOfUser.CANTEEN) {
                    return true;
                }

                break;
            }

            case 92: {
                if(typeOfUser == TypeOfUser.GROCERY_STORE) {
                    return true;
                }

                break;
            }

            case 93: {
                if(typeOfUser == TypeOfUser.RESTAURANT) {
                    return true;
                }

                break;
            }
        }

        return false;
    }

    private void checkContributorIdAndType(TypeOfUser typeOfUser, String ContributorID) {
        if(!isValidTypeOfUser(typeOfUser, ContributorID)) {
            throw new IllegalArgumentException("Contributor ID is not valid or invalid type of contributor");
        }
    }

    public Contributor(String username, String name, String email, String password, String phoneNumber, String address, String contributorID, String typeOfContributor, String description) {
        super(username, name, email, password, phoneNumber, address);
        this.setTypeOfUser(typeOfContributor);
        this.ContributorID = contributorID;
        checkContributorIdAndType(this.typeOfUser, this.ContributorID);
        this.setDescription(description);
    }

    public void setTypeOfUser(String typeOfContributor) {
        switch (typeOfContributor.toUpperCase()) {
            case "CANTEEN" -> this.typeOfUser = TypeOfUser.CANTEEN;
            case "RESTAURANT" -> this.typeOfUser = TypeOfUser.RESTAURANT;
            case "GROCERY_STORE" -> this.typeOfUser = TypeOfUser.GROCERY_STORE;
        }
    }

    public void setDescription(String description){
        if(description == null || description.isEmpty()) {
            this.description = "[No description provided]";
        }

        else {
            this.description = description;
        }
    }
}
