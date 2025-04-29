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

    private static long canteenId = 91000;
    private static long groceryStoreId = 92000;
    private static long restaurantId = 93000;

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

    //private void checkContributorIdAndType(TypeOfUser typeOfUser, String ContributorID) {
    //    if(!isValidTypeOfUser(typeOfUser, ContributorID)) {
    //        throw new IllegalArgumentException("Contributor ID is not valid or invalid type of contributor");
    //    }
    //}

    @Override
    protected long generateId()
    {
        switch(this.typeOfUser)
        {
            case CANTEEN:
                if(canteenId < 92000) {
                    return canteenId++;
                }
                else throw new IllegalArgumentException("The database has enough canteen contributors");
            case GROCERY_STORE:
                if(groceryStoreId < 93000) {
                    return groceryStoreId++;
                }
                else throw new IllegalArgumentException("The database has enough grocery store contributors");
            case RESTAURANT:
                if(restaurantId < 94000) {
                    return restaurantId++;
                }
                else throw new IllegalArgumentException("The database has enough restaurant contributors");
            default:
                throw new IllegalArgumentException("Invalid type of contributor");
        }
    }

    public Contributor(String username, String name, String email, String password, String phoneNumber, String address, String typeOfContributor, String description) {
        super(username, name, email, password, phoneNumber, address);
        this.setTypeOfUser(typeOfContributor);
        this.setId(generateId());
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
