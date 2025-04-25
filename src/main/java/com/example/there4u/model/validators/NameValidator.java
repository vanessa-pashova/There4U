package com.example.there4u.model.validators;

public class NameValidator {
    public static String validateName(String name) {
        if(name.length() < 2)
            throw new IllegalArgumentException(">! Name must contain at least 2 symbols [validateName(), NameValidator]");

        //Deletes unnecessary spaces at the beginning and at the end
        name = name.trim();
        StringBuilder formattedName = new StringBuilder();
        boolean capitalLetter = true;
        char previousSymbol = '\0';

        for (char current : name.toCharArray()) {
            //If the current symbol is not a letter or a space
            if (!Character.isLetter(current) && current != ' ') {
                throw new IllegalArgumentException(">! Name must only contain letters [validateName(), NameValidator]");
            }

            //Multiple spaces in a row error
            if (current == ' ' && previousSymbol == ' ') {
                throw new IllegalArgumentException(">! Name contains multiple spaces in a row [validateName(), NameValidator]");
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

        return name = formattedName.toString();
    }
}
