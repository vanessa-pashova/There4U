package com.example.there4u.model.user;

import com.example.there4u.model.validators.EmailValidator;
import com.example.there4u.model.validators.NameValidator;
import com.example.there4u.model.validators.PasswordValidator;
import com.example.there4u.model.validators.PhoneNumberValidator;
import com.example.there4u.service.geo.OSMBatchAddressValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
 *
 * Validation:
 * All setters include strict validation rules to ensure data integrity and consistent formatting.
 *
 * Dependencies:
 * - EmailValidator: Utility for validating email addresses.
 * - PhoneNumberValidator: Utility for validating phone numbers.
 * - OSMBatchAddressValidator: Service for validating physical addresses using OSM data.
 *
 * Example usage (in subclasses):
 * public class RegisteredUser extends User { ... }
 *
 * @author
 */

@Getter
@NoArgsConstructor
public abstract class User {
    protected String name;
    protected String email;
    protected String password;
    protected String phone;
    protected String address;

    @Setter
    protected TypeOfUser typeOfUser;

    public User(String name, String email, String password, String phone, String address, TypeOfUser typeOfUser) {
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.setPhone(phone);
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
