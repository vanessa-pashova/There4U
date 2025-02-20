package com.example.there4u.models;

import com.example.there4u.repositories.UserRepository;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ANONYMOUS")
public class AnonymousUser extends User {
    public AnonymousUser(UserRepository userRepository) {
        super(true, userRepository);
    }

    public AnonymousUser() {
        super();
        this.setAnonymousDefaults();
    }

    private void setAnonymousDefaults() {
        this.username = "AnonymousUser"; //Guarantee that the username is always AnonymousUser
    }

    @Override
    public boolean isAnonymous() {
        return true;
    }
}
