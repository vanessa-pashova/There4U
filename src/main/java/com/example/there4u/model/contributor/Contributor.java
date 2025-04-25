package com.example.there4u.model.contributor;

import com.example.there4u.model.validators.*;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Contributor {
    @Setter
    TypeOfContributor typeOfContributor;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private long contributorID;
    private String description;

    public Contributor(TypeOfContributor typeOfContributor, String name, String email, String password, String phoneNumber, long contributorID, String description) {
        this.setTypeOfContributor(typeOfContributor);
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.setPhoneNumber(phoneNumber);
        this.setContributorID(contributorID);
        this.setDescription(description);
    }

    public void setName(String name){
        this.name = NameValidator.validateName(name);
    }

    public void setEmail(String email){
        if(!EmailValidator.isValidEmail(email)){
            throw new IllegalArgumentException(">! Invalid email address [setEmail(), Contributor]");
        }

        this.email = email;
    }

    public void setPassword(String password){
        if(PasswordValidator.validatePassword(password)) {
            this.password = password;
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        if(!PhoneNumberValidator.isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException(">! Invalid phone number [setPhoneNumber(), Contributor]");
        }

        this.phoneNumber = phoneNumber;
    }

    public void setContributorID(long contributorID){
        if(ContributorIdValidator.isValidContributorId(contributorID)) {
            this.contributorID = contributorID;
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
