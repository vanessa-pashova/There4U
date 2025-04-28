package com.example.there4u.model.validators;

public class PasswordValidator {
    public static boolean validatePassword(String password) {
        //The password must be at least 8 characters
        if(password.length() < 8) {
            throw new IllegalArgumentException(">! Password must contain at least 8 symbols [validatePassword(), PasswordValidator]");
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
            throw new IllegalArgumentException(">! Password must contain at least one: capital letter, small letter and a digit [validatePassword(), PasswordValidator]");
        }

        return true;
    }
}
