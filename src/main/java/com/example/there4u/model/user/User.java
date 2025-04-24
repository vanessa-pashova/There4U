package com.example.there4u.model.user;

import com.example.there4u.model.validators.EmailValidator;
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
        if(name.length() < 2)
            throw new IllegalArgumentException(">! Name must contain at least 2 symbols [setName(), User]");

        //Deletes unnecessary spaces at the beginning and at the end
        name = name.trim();
        StringBuilder formattedName = new StringBuilder();
        boolean capitalLetter = true;
        char previousSymbol = '\0';

        for (char current : name.toCharArray()) {
            //If the current symbol is not a letter or a space
            if (!Character.isLetter(current) && current != ' ') {
                throw new IllegalArgumentException(">! Name must only contain letters [setName(), User]");
            }

            //Multiple spaces in a row error
            if (current == ' ' && previousSymbol == ' ') {
                throw new IllegalArgumentException(">! Name contains multiple spaces in a row [setName(), User]");
            }

            //Capitalizing the next letter if the current one is space
            if (current == ' ') {
                formattedName.append(' ');
                capitalLetter = true;
            } else {
                //Check if we should capitalize or not
                formattedName.append(capitalLetter ? Character.toUpperCase(current) : Character.toLowerCase(current));
                capitalLetter = false;
            }

            previousSymbol = current;
        }

        this.name = formattedName.toString();
    }

    public void setEmail(String email) {
        if(!EmailValidator.isValidEmail(email)) {
            throw new IllegalArgumentException(">! Invalid email address [setEmail(), User]");
        }

        this.email = email.toLowerCase();
    }

    public void setPassword(String password) {
        //The password must be at least 8 characters
        if(password.length() < 8) {
            throw new IllegalArgumentException(">! Password must contain at least 8 symbols [setPassword(), User]");
        }

        boolean containsCapitalLetter = false,
                containsSmallLetter = false,
                containsDigit = false;

        for(char current : password.toCharArray()) {
            //If the password contains at least one capital letter
            if(Character.isLetter(current) && Character.isUpperCase(current)) {
                containsCapitalLetter = true;
            }

            //If the password contains at least one small letter
            else if(Character.isLetter(current) && Character.isLowerCase(current)) {
                containsSmallLetter = true;
            }

            //If the password contains at least one digit
            else if(Character.isDigit(current)) {
                containsDigit = true;
            }
        }

        //If at least one is not satisfying the conditions above, then error
        if(!(containsCapitalLetter && containsSmallLetter && containsDigit)) {
            throw new IllegalArgumentException(">! Password must contain at least one: capital letter, small letter and a digit [setPassword(), User]");
        }

        this.password = password;
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
