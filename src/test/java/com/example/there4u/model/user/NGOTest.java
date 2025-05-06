package com.example.there4u.model.user;

import jakarta.validation.*;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class NGOTest {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private NGOUser ngoUser = new NGOUser(
            "charity_lover",
            "Maria Ivanova",
            "maria@charity.com",
            "StrongPass1",
            "0881234567",
            "Bulgaria 12, Sofia",
            "Helping homeless people"
    );

    // --- General Creation Test ---

    @Test
    public void validNGOUserCreation() {
        assertEquals("charity_lover", ngoUser.getUsername());
        assertEquals("Maria Ivanova", ngoUser.getName());
        assertEquals("maria@charity.com", ngoUser.getEmail());
        assertEquals("StrongPass1", ngoUser.getPassword());
        assertEquals("0881234567", ngoUser.getPhone());
        assertEquals("Bulgaria 12, Sofia", ngoUser.getAddress());
        assertEquals("Helping homeless people", ngoUser.getDescription());
        assertEquals(TypeOfUser.NGO, ngoUser.getTypeOfUser());
    }

    // --- Description tests ---

    @Test
    public void validDescription_Set() {
        ngoUser.setDescription("We help people in need");
        assertEquals("We help people in need", ngoUser.getDescription());
    }

    @Test
    public void emptyDescription_ShouldSetDefaultMessage() {
        ngoUser.setDescription("");
        assertEquals("[No description provided]", ngoUser.getDescription());
    }

    @Test
    public void nullDescription_ShouldSetDefaultMessage() {
        ngoUser.setDescription(null);
        assertEquals("[No description provided]", ngoUser.getDescription());
    }

    // --- Username validation inherited fromEntity User ---

    @Test
    public void invalidUsername_WithSpecialSymbols() {
        ngoUser.username = "charity@lover!";
        Set<ConstraintViolation<NGOUser>> violations = validator.validateProperty(ngoUser, "username");
        assertFalse(violations.isEmpty());
    }

    // --- Name validation inherited fromEntity User ---

    @Test
    public void invalidName_NoCapitalLetter() {
        ngoUser.name = "maria ivanova";
        Set<ConstraintViolation<NGOUser>> violations = validator.validateProperty(ngoUser, "name");
        assertFalse(violations.isEmpty());
    }

    // --- Email validation inherited fromEntity User ---

    @Test
    public void invalidEmail_WrongFormat() {
        ngoUser.email = "mariacharity.com";
        Set<ConstraintViolation<NGOUser>> violations = validator.validateProperty(ngoUser, "email");
        assertFalse(violations.isEmpty());
    }

    // --- Password validation inherited fromEntity User ---

    @Test
    public void invalidPassword_MissingDigit() {
        ngoUser.password = "StrongPassword";
        Set<ConstraintViolation<NGOUser>> violations = validator.validateProperty(ngoUser, "password");
        assertFalse(violations.isEmpty());
    }

    // --- Phone validation inherited fromEntity User ---

    @Test
    public void invalidPhone_InvalidPrefix() {
        ngoUser.phone = "0861234567";
        Set<ConstraintViolation<NGOUser>> violations = validator.validateProperty(ngoUser, "phone");
        assertFalse(violations.isEmpty());
    }
}
