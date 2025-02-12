package com.example.there4u.models;

import com.example.there4u.repositories.UserRepository;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Random;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)

public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = ">! Username must not be empty.")
    @Size(max = 30, message = ">! Username cannot contain more than 30 symbols.")
    private String username;

    private boolean anonymous;

    @Transient
    private static final Random random = new Random();

    //Constructor
    public User(boolean anonymous, UserRepository userRepository) {
        this.setId(userRepository);
        this.anonymous = anonymous;

        if(anonymous)
            this.setUsername("Anonymous");
    }

    //Default Constructor
    public User() {}

    //Getters for ID and username
    public long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    //Generator for ID between 100,000 and 999,999
    private static long generateUniqueId(UserRepository userRepository) {
        long newId;

        do {
            newId = 100000l + random.nextLong(900000);
        } while(userRepository.existsById(newId));

        return newId;
    }

    //Setter for ID as we need to generate one to be unique
    public void setId(UserRepository userRepository) {
        this.id = generateUniqueId(userRepository);
    }

    private boolean validUsername(String username) {
        //Checking if username is NULL, empty or with invalid length
        if(username == null || username.isBlank()) return false;
        if(username.length() < 3 || username.length() > 30) return false;

        //We check if there are any not allowed symbols
        for(char ch : username.toCharArray()) {
            if(!Character.isLetterOrDigit(ch) && ch != '-' && ch != '_' && ch != '.')
                return false;
        }

        //Must contain at least one letter
        boolean hasLetter = username.chars().anyMatch(Character::isLetter);
        if(!hasLetter)
            return false;

        //If everything is fine - return true
        return true;
    }

    public void setUsername(String username) {
        //We check if the username is valid
        if(this.validUsername(username))
            this.username = username;

        //If it's not valid, we throw an error
        else throw new IllegalArgumentException(">! Invalid username.");
    }
}