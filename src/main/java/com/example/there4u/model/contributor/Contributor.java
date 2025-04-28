package com.example.there4u.model.contributor;

import com.example.there4u.model.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contributor extends User {
    TypeOfContributor typeOfContributor;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ContributorID")
    @NotNull(message = ">! ContributorID cannot be null")
    @Pattern(regexp = "^(91|92|93)[0-9]{3}$", message = "Contributor ID must start with 91, 92 or 93 and have exactly 5 digits.")
    private String ContributorID;

    @Column(name = "description")
    private String description = "[No description added]";

    private boolean isValidTypeOfContributor(TypeOfContributor typeOfContributor, String ContributorID) {
        int code = Integer.parseInt(ContributorID.substring(0, 2));
        switch(code) {
            case 91: {
                if(typeOfContributor == TypeOfContributor.CANTEEN) {
                    return true;
                }

                break;
            }

            case 92: {
                if(typeOfContributor == TypeOfContributor.GROCERY_STORE) {
                    return true;
                }

                break;
            }

            case 93: {
                if(typeOfContributor == TypeOfContributor.RESTAURANT) {
                    return true;
                }

                break;
            }
        }

        return false;
    }

    private void checkContributorIdAndType(TypeOfContributor typeOfContributor, String ContributorID) {
        if(!isValidTypeOfContributor(typeOfContributor, ContributorID)) {
            throw new IllegalArgumentException("Contributor ID is not valid or invalid type of contributor");
        }
    }

    public Contributor(String username, String name, String email, String password, String phoneNumber, String address, String contributorID, String typeOfContributor, String description) {
        super(username, name, email, password, phoneNumber, address);
        this.setTypeOfContributor(typeOfContributor);
        this.ContributorID = contributorID;
        checkContributorIdAndType(this.typeOfContributor, this.ContributorID);
        this.setDescription(description);
    }

    public void setTypeOfContributor(String typeOfContributor) {
        switch (typeOfContributor.toUpperCase()) {
            case "CANTEEN" -> this.typeOfContributor = TypeOfContributor.CANTEEN;
            case "RESTAURANT" -> this.typeOfContributor = TypeOfContributor.RESTAURANT;
            case "GROCERY_STORE" -> this.typeOfContributor = TypeOfContributor.GROCERY_STORE;
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
