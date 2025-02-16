package com.example.there4u.models;

import com.example.there4u.repositories.UserRepository;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@DiscriminatorValue("REGISTERED")
@Entity
public class RegisteredUser extends User {
    @Column(unique = false, nullable = false)
    @NotBlank(message = ">! Personal names must not be empty")
    @Size(min = 2, max = 20, message = ">! Names must be [2, 20] characters long")
    private String firstName, lastName;

    @Column(unique = true, nullable = false)
    @NotBlank(message = ">! Email must not be null")
    @Size(min = 13, max = 40, message = ">! Invalid email length")
    private String email;

    @Column(unique = false, nullable = false)
    @NotBlank(message = ">! Password must not be null")
    @Size(min = 8, max = 20, message = "Invalid password length")
    private String password;

    @Column(unique = true, nullable = true)
    @Size(min = 10, max = 10, message = ">! Invalid phone number length")
    private String phoneNumber;

    @Column(unique = false, nullable = false)
    @Size(max = 512, message = ">! Must not exceed 512 characters")
    private String bio;

    @Embedded
    private Level level;

    //Constructors
    public RegisteredUser(UserRepository userRepository, String firstName, String lastName, String email, String password, String phoneNumber, String bio) {
        super(false, userRepository);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email, userRepository);
        this.setPassword(password);
        this.setPhoneNumber(phoneNumber, userRepository);
        this.setBio(bio);
        this.level = new Level();
    }

    public RegisteredUser() {
        super();
        this.level = new Level();
    }

    //Getters
    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getBio() {
        return this.bio;
    }

    public Level getLevel() {
        return this.level;
    }

    //Setters for validity
    //Setters for the personal names of the user: starting with capital letter and the rest is with small letters
    public void setFirstName(String firstName) {
        if(firstName == null || firstName.isEmpty())
            throw new IllegalArgumentException(">! First name cannot be null or empty");

        if(firstName.length() < 2)
            throw new IllegalArgumentException(">! First name cannot be less than 2 characters");

        else if(firstName.length() > 20)
            throw new IllegalArgumentException(">! First name cannot exceed 20 characters");

        if (!firstName.chars().allMatch(Character::isLetter))
            throw new IllegalArgumentException(">! First name contains invalid characters");

        this.firstName = Character.toUpperCase(firstName.charAt(0)) + firstName.substring(1);
    }

    public void setLastName(String lastName) {
        if(lastName == null || lastName.isEmpty())
            throw new IllegalArgumentException(">! Last name cannot be null or empty");

        if(lastName.length() < 2)
            throw new IllegalArgumentException(">! Last name cannot be less than 2 characters");

        else if(lastName.length() > 20)
            throw new IllegalArgumentException(">! Last name cannot exceed 20 characters");

        if (!lastName.chars().allMatch(Character::isLetter))
            throw new IllegalArgumentException(">! Last name contains invalid characters");

        this.lastName = Character.toUpperCase(lastName.charAt(0)) + lastName.substring(1);
    }

    public void setEmail(String email, UserRepository userRepository) {
        //Check if email is empty
        if(email == null || email.isEmpty())
            throw new IllegalArgumentException(">! Email cannot be null or empty");

        //Check if email length is between [13 and 40]
        if(email.length() < 13)
            throw new IllegalArgumentException(">! Email cannot be less than 13 characters");

        else if(email.length() > 40)
            throw new IllegalArgumentException(">! Email is too long");

        //Check if the email ends with the following domains
        if(!(email.endsWith("@gmail.com") || email.endsWith("@yahoo.com") || email.endsWith("@outlook.com") || email.endsWith("@icloud.com") || email.endsWith("@abv.bg")))
            throw new IllegalArgumentException(">! Email address must contain one of the following domains: @gmail.com | @yahoo.com | @outlook.com | @icloud.com | @abv.bg");

        //Check if the email is used by someone else
        if(userRepository.existsByEmail(email))
            throw new IllegalArgumentException(">! This email is already in use");

        this.email = email.toLowerCase();
    }

    public void setPassword(String password) {
        //Check if password is empty
        if(password == null || password.isEmpty())
            throw new IllegalArgumentException(">! Password cannot be null or empty");

        //Check if password length is [8,20] characters long
        if(password.length() < 8 || password.length() > 20)
            throw new IllegalArgumentException(">! Password must contain [8, 20] characters");

        //Check if password contains at least one capital letter, one small letter and at least one digit
        boolean hasLowerLetter = false, hasUpperLetter = false, hasDigit = false;
        for(char c : password.toCharArray()) {
            if(Character.isLowerCase(c))
                hasLowerLetter = true;

            if(Character.isUpperCase(c))
                hasUpperLetter = true;

            if(Character.isDigit(c))
                hasDigit = true;
        }

        //If at least one of these conditions is false then:
        if(!hasLowerLetter && !hasUpperLetter && !hasDigit)
            throw new IllegalArgumentException(">! Password must contain at least one lower case letter, one capital letter and one digit");

        else this.password = password;
    }

    public void setPhoneNumber(String phoneNumber, UserRepository userRepository) {
        //Phone number must be empty or contains exactly 10 digits
        if(phoneNumber.length() != 0 || phoneNumber.length() != 10)
            throw new IllegalArgumentException(">! Invalid phone number length");

        //Must start with 087, 088 or 089
        if (!(phoneNumber.startsWith("087") || phoneNumber.startsWith("088") || phoneNumber.startsWith("089")))
            throw new IllegalArgumentException(">! Phone number must start with 087, 088, or 089");

        //Must contain only digits
        for(char c : phoneNumber.toCharArray()) {
            if(!Character.isDigit(c))
                throw new IllegalArgumentException(">! Phone number must contain only digits");
        }

        //Must not be used by someone else
        if(userRepository.existsPhoneNumber(phoneNumber))
            throw new IllegalArgumentException(">! This phone number is already in use");

        this.phoneNumber = phoneNumber;
    }

    public void setBio(String bio) {
        //Must not exceed 512 characters
        if(bio.length() > 512)
            throw new IllegalArgumentException(">! Bio cannot be longer than 512 characters");

        this.bio = bio;
    }

    //Method for adding progress to the current level
    public void addLevelProgress(double progress) {
        this.level.addProgress(progress);
    }
}
