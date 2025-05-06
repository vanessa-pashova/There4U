package com.example.there4u.model.user;

import jakarta.validation.*;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RegularUserTest {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private RegularUser regularUser = new RegularUser(
            "papi_chulo",
            "Petar Georgiev",
            "papi@icloud.com",
            "Password1",
            "0882340592",
            "bul. James Bourchier",
            "1234567890"
    );

    // --- General tests ---

    @Test
    public void validRegularUserCreation() {
        assertEquals("papi_chulo", regularUser.getUsername());
        assertEquals("Petar Georgiev", regularUser.getName());
        assertEquals("papi@icloud.com", regularUser.getEmail());
        assertEquals("Password1", regularUser.getPassword());
        assertEquals("0882340592", regularUser.getPhone());
        assertEquals("bul. James Bourchier", regularUser.getAddress());
        assertEquals("1234567890", regularUser.getUCN());
        assertEquals(TypeOfUser.REGULAR_USER, regularUser.getTypeOfUser());
    }

    // --- UCN (Civil Number) validation tests ---

    @Test
    public void validUCN() {
        regularUser.setUCN("1234567890");
        Set<ConstraintViolation<RegularUser>> violations = validator.validateProperty(regularUser, "UCN");
        assertTrue(violations.isEmpty());
    }

    @Test
    public void invalidUCN_TooShort() {
        regularUser.setUCN("12345");
        Set<ConstraintViolation<RegularUser>> violations = validator.validateProperty(regularUser, "UCN");
        assertFalse(violations.isEmpty());
    }

    @Test
    public void invalidUCN_TooLong() {
        regularUser.setUCN("1234567890123");
        Set<ConstraintViolation<RegularUser>> violations = validator.validateProperty(regularUser, "UCN");
        assertFalse(violations.isEmpty());
    }

    @Test
    public void invalidUCN_WithLetters() {
        regularUser.setUCN("12345ABCD1");
        Set<ConstraintViolation<RegularUser>> violations = validator.validateProperty(regularUser, "UCN");
        assertFalse(violations.isEmpty());
    }

    @Test
    public void invalidUCN_SpecialCharacters() {
        regularUser.setUCN("12345@#678");
        Set<ConstraintViolation<RegularUser>> violations = validator.validateProperty(regularUser, "UCN");
        assertFalse(violations.isEmpty());
    }

    // --- Username validation inherited fromEntity User ---

    @Test
    public void invalidUsername_WithSpecialSymbols() {
        regularUser.username = "papi@chulo!";
        Set<ConstraintViolation<RegularUser>> violations = validator.validateProperty(regularUser, "username");
        assertFalse(violations.isEmpty());
    }

    // --- Name validation inherited fromEntity User ---

    @Test
    public void invalidName_LowercaseStart() {
        regularUser.name = "petar georgiev";
        Set<ConstraintViolation<RegularUser>> violations = validator.validateProperty(regularUser, "name");
        assertFalse(violations.isEmpty());
    }

    // --- Email validation inherited fromEntity User ---

    @Test
    public void invalidEmail_WrongFormat() {
        regularUser.email = "papiicloud.com";
        Set<ConstraintViolation<RegularUser>> violations = validator.validateProperty(regularUser, "email");
        assertFalse(violations.isEmpty());
    }

    // --- Password validation inherited fromEntity User ---

    @Test
    public void invalidPassword_MissingDigit() {
        regularUser.password = "Password";
        Set<ConstraintViolation<RegularUser>> violations = validator.validateProperty(regularUser, "password");
        assertFalse(violations.isEmpty());
    }

    // --- Phone validation inherited fromEntity User ---

    @Test
    public void invalidPhone_InvalidPrefix() {
        regularUser.phone = "0861234567";
        Set<ConstraintViolation<RegularUser>> violations = validator.validateProperty(regularUser, "phone");
        assertFalse(violations.isEmpty());
    }
}
