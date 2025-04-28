package com.example.there4u.model.validators;

import java.util.Set;

/**
 * Utility class for validating email addresses based on a regex pattern
 * and a set of allowed email domains.
 *
 * <p>Supported domains include:
 * <ul>
 *     <li>@gmail.com</li>
 *     <li>@abv.bg</li>
 *     <li>@yahoo.com</li>
 *     <li>@mail.com</li>
 *     <li>@icloud.com</li>
 *     <li>@outlook.com</li>
 *     <li>@hotmail.com</li>
 * </ul>
 *
 * <p>The email must also follow a general format of:
 * <code>username@domain</code> where the domain ends in at least 2 alphabetic characters.
 *
 */

public class EmailValidator {

    /* Regular expression pattern for basic email format validation.
       Accepts alphanumeric usernames with '.', '-', or '_' and a valid domain. */
    private static final String EMAIL_PATTERN = "^[\\w.-_]+@[\\w.]+[a-z]{2,}$";

    //List of accepted email domains.
    private static final Set<String> allowedDomains = Set.of("@gmail.com",
                                                            "@abv.bg",
                                                            "@yahoo.com",
                                                            "@mail.com",
                                                            "@icloud.com",
                                                            "@outlook.com",
                                                            "@hotmail.com");

    //validates email domain
    public static boolean isValidEmail(String email) {
        if(!email.matches(EMAIL_PATTERN)) {
            return false;
        }

        String domain = email.substring(email.indexOf('@'));
        return allowedDomains.contains(domain);
    }
}
