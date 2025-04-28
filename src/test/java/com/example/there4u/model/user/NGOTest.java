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
            "91123",
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
        assertEquals("91123", ngoUser.getNGOid());
        assertEquals("Helping homeless people", ngoUser.getDescription());
        assertEquals(TypeOfUser.NGO, ngoUser.getTypeOfUser());
    }

    // --- NGOid validation tests ---

    @Test
    public void validNGOid() {
        ngoUser.setNGOid("92123");
        Set<ConstraintViolation<NGOUser>> violations = validator.validateProperty(ngoUser, "NGOid");
        assertTrue(violations.isEmpty());
    }

    @Test
    public void invalidNGOid_StartsWrong() {
        ngoUser.setNGOid("81123"); // not starting with 91, 92 or 93
        Set<ConstraintViolation<NGOUser>> violations = validator.validateProperty(ngoUser, "NGOid");
        assertFalse(violations.isEmpty());
    }

    @Test
    public void invalidNGOid_WrongLength() {
        ngoUser.setNGOid("9212"); // only 4 digits
        Set<ConstraintViolation<NGOUser>> violations = validator.validateProperty(ngoUser, "NGOid");
        assertFalse(violations.isEmpty());
    }

    @Test
    public void invalidNGOid_ContainsLetters() {
        ngoUser.setNGOid("92a23"); // contains letter 'a'
        Set<ConstraintViolation<NGOUser>> violations = validator.validateProperty(ngoUser, "NGOid");
        assertFalse(violations.isEmpty());
    }

    @Test
    public void invalidNGOid_Null() {
        ngoUser.setNGOid(null);
        Set<ConstraintViolation<NGOUser>> violations = validator.validateProperty(ngoUser, "NGOid");
        assertFalse(violations.isEmpty());
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

    // --- Username validation inherited from User ---

    @Test
    public void invalidUsername_WithSpecialSymbols() {
        ngoUser.username = "charity@lover!";
        Set<ConstraintViolation<NGOUser>> violations = validator.validateProperty(ngoUser, "username");
        assertFalse(violations.isEmpty());
    }

    // --- Name validation inherited from User ---

    @Test
    public void invalidName_NoCapitalLetter() {
        ngoUser.name = "maria ivanova";
        Set<ConstraintViolation<NGOUser>> violations = validator.validateProperty(ngoUser, "name");
        assertFalse(violations.isEmpty());
    }

    // --- Email validation inherited from User ---

    @Test
    public void invalidEmail_WrongFormat() {
        ngoUser.email = "mariacharity.com";
        Set<ConstraintViolation<NGOUser>> violations = validator.validateProperty(ngoUser, "email");
        assertFalse(violations.isEmpty());
    }

    // --- Password validation inherited from User ---

    @Test
    public void invalidPassword_MissingDigit() {
        ngoUser.password = "StrongPassword";
        Set<ConstraintViolation<NGOUser>> violations = validator.validateProperty(ngoUser, "password");
        assertFalse(violations.isEmpty());
    }

    // --- Phone validation inherited from User ---

    @Test
    public void invalidPhone_InvalidPrefix() {
        ngoUser.phone = "0861234567";
        Set<ConstraintViolation<NGOUser>> violations = validator.validateProperty(ngoUser, "phone");
        assertFalse(violations.isEmpty());
    }
}
