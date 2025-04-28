package com.example.there4u.model.validators;

/**
 * Utility class for validating Bulgarian phone numbers.
 * <p>
 * This validator supports:
 * <ul>
 *     <li>Bulgarian mobile phone numbers starting with:
 *         <ul>
 *             <li><code>+359</code> (international format)</li>
 *             <li><code>00359</code> (international format)</li>
 *             <li><code>0</code> (local format)</li>
 *         </ul>
 *         followed by a digit between 87–89 and exactly 7 digits after that.
 *     </li>
 *     <li>Sofia landline numbers (city code 2) with exactly 7 digits.</li>
 * </ul>
 *
 * <p>Example of valid numbers:
 * <ul>
 *     <li>+359888123456</li>
 *     <li>0888123456</li>
 *     <li>00359888123456</li>
 *     <li>29876543 (landline)</li>
 * </ul>
 *
 */
public class PhoneNumberValidator {
    //Regex for validating Bulgarian mobile phone numbers.
    private static final String MOBILE_PATTERN = "^(?:\\+359|00359|0)8[7-9][0-9]{7}$";
    private static final String LANDLINE_PATTERN = "^2[0-9]{7}$";

    //Validates a Bulgarian phone number.
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches(MOBILE_PATTERN) || phoneNumber.matches(LANDLINE_PATTERN);
    }
}
