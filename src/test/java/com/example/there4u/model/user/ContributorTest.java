package com.example.there4u.model.user;

import jakarta.validation.*;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ContributorTest {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private Contributor contributor = new Contributor(
            "canteen_master",
            "Ivan Petrov",
            "ivan_canteen@yahoo.com",
            "SecurePass1",
            "0887654321",
            "bul. Vitosha 15, Sofia",
            "CANTEEN",
            "Providing free meals"
    );

    // --- General creation test ---

    @Test
    public void validContributorCreation() {
        assertEquals("canteen_master", contributor.getUsername());
        assertEquals("Ivan Petrov", contributor.getName());
        assertEquals("ivan_canteen@yahoo.com", contributor.getEmail());
        assertEquals("SecurePass1", contributor.getPassword());
        assertEquals("0887654321", contributor.getPhone());
        assertEquals("bul. Vitosha 15, Sofia", contributor.getAddress());
        assertEquals(TypeOfUser.CANTEEN, contributor.getTypeOfUser());
        assertEquals("Providing free meals", contributor.getDescription());
    }

    // --- Description tests ---

    @Test
    public void validDescription_Set() {
        contributor.setDescription("Best canteen in town");
        assertEquals("Best canteen in town", contributor.getDescription());
    }

    @Test
    public void emptyDescription_ShouldSetDefaultMessage() {
        contributor.setDescription("");
        assertEquals("[No description provided]", contributor.getDescription());
    }

    @Test
    public void nullDescription_ShouldSetDefaultMessage() {
        contributor.setDescription(null);
        assertEquals("[No description provided]", contributor.getDescription());
    }

    // --- Type of Contributor matching tests ---

    @Test
    public void validType_Matching() {
        Contributor validRestaurantContributor = new Contributor(
                "restaurant_guru",
                "Georgi Ivanov",
                "georgi.restaurant@gmail.com",
                "MyPass123",
                "0897654321",
                "ul. Rakovski 100, Sofia",
                "RESTAURANT",
                "Fine dining experience"
        );

        assertEquals(TypeOfUser.RESTAURANT, validRestaurantContributor.getTypeOfUser());
    }

    // --- Username validation inherited from User ---

    @Test
    public void invalidUsername_SpecialSymbols() {
        contributor.username = "canteen@master!";
        Set<ConstraintViolation<Contributor>> violations = validator.validateProperty(contributor, "username");
        assertFalse(violations.isEmpty());
    }

    // --- Name validation inherited from User ---

    @Test
    public void invalidName_LowercaseStart() {
        contributor.setName("ivan petrov");
        Set<ConstraintViolation<Contributor>> violations = validator.validateProperty(contributor, "name");
        assertFalse(violations.isEmpty());
    }

    // --- Email validation inherited from User ---

    @Test
    public void invalidEmail_NoAtSymbol() {
        contributor.setEmail("ivangmail.com");
        Set<ConstraintViolation<Contributor>> violations = validator.validateProperty(contributor, "email");
        assertFalse(violations.isEmpty());
    }

    // --- Password validation inherited from User ---

    @Test
    public void invalidPassword_MissingDigit() {
        contributor.setPassword("SecurePassword");
        Set<ConstraintViolation<Contributor>> violations = validator.validateProperty(contributor, "password");
        assertFalse(violations.isEmpty());
    }

    // --- Phone validation inherited from User ---

    @Test
    public void invalidPhone_WrongPrefix() {
        contributor.phone = "0861234567";
        Set<ConstraintViolation<Contributor>> violations = validator.validateProperty(contributor, "phone");
        assertFalse(violations.isEmpty());
    }
}
