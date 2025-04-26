package com.example.there4u.model.user;

import com.example.there4u.model.validators.EmailValidator;
import com.example.there4u.model.validators.NameValidator;
import com.example.there4u.model.validators.PasswordValidator;
import com.example.there4u.model.validators.PhoneNumberValidator;
import com.example.there4u.service.geo.OSMBatchAddressValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

/**
 * Abstract class representing a generic user in the system.
 * Provides common fields and validation logic for all user types.
 * This class is intended to be extended by specific user roles.
 *
 * Fields:
 * - name: User's full name, formatted with capitalization and validated for alphabetic characters only.
 * - email: User's email address, validated for standard email format.
 * - password: Must be at least 8 characters, including at least one uppercase letter, one lowercase letter, and one digit.
 * - phone: User's phone number, validated using custom logic.
 * - address: User's address, validated through OpenStreetMap API or equivalent validator.
 * - typeOfUser: Enum defining the type of user (e.g., anonymous, registered, moderator, etc.).
 */

@Getter
@NoArgsConstructor
public abstract class User {
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{4,19}$", message = "!> Invalid username. Username must contain only letters, numbers or underscores. And must be 4-19 characters long.")
    protected String username;

    @Pattern(regexp = "^[A-Z][a-z]+( [A-Z][a-z]+)*$", message = "!> Invalid name")
    protected String name;

    @Email
    protected String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "!> Invalid password. Password must contain at least 8 symbols from which at least one uppercase letter, at least one lowercase letter, and at least one number")
    protected String password;

    @Pattern(regexp = "^(?:(?:\\+359|00359|0)8[7-9][0-9]{7}|2[0-9]{7})$", message = "!> Invalid phone")
    protected String phone;

    protected String address;

    @Setter
    protected TypeOfUser typeOfUser;

    public User(String username, String name, String email, String password, String phone, String address, TypeOfUser typeOfUser) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.setAddress(address);
        this.setTypeOfUser(typeOfUser);
    }

    public void setName(String name) {
       this.name = NameValidator.validateName(name);
    }

    public void setEmail(String email) {
        if(!EmailValidator.isValidEmail(email)) {
            throw new IllegalArgumentException(">! Invalid email address [setEmail(), User]");
        }

        this.email = email.toLowerCase();
    }

    public void setPassword(String password) {
        if(PasswordValidator.validatePassword(password)) {
            this.password = password;
        }
    }

    public void setPhone(String phone) {
        if(!PhoneNumberValidator.isValidPhoneNumber(phone)) {
            throw new IllegalArgumentException(">! Invalid phone number [setPhone(), User]");
        }

        this.phone = phone;
    }

    public void setAddress(String address) {
        if(!OSMBatchAddressValidator.isValidAddress(address)) {
            throw new IllegalArgumentException(">! Invalid address [setAddress(), User]");
        }

        this.address = address;
    }
}
