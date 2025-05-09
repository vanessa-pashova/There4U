package com.example.there4u.model.user;

import com.example.there4u.model.contributor.Contributor;
import com.example.there4u.model.contributor.TypeOfContributor;
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
            "91123",
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
        assertEquals("91123", contributor.getContributorID());
        assertEquals(TypeOfContributor.CANTEEN, contributor.getTypeOfContributor());
        assertEquals("Providing free meals", contributor.getDescription());
    }

    // --- ContributorID validation tests ---

    @Test
    public void validContributorID() {
        contributor.setContributorID("92123");
        Set<ConstraintViolation<Contributor>> violations = validator.validateProperty(contributor, "ContributorID");
        assertTrue(violations.isEmpty());
    }

    @Test
    public void invalidContributorID_WrongPrefix() {
        contributor.setContributorID("80123");
        Set<ConstraintViolation<Contributor>> violations = validator.validateProperty(contributor, "ContributorID");
        assertFalse(violations.isEmpty());
    }

    @Test
    public void invalidContributorID_WrongLength() {
        contributor.setContributorID("9112"); // 4 digits
        Set<ConstraintViolation<Contributor>> violations = validator.validateProperty(contributor, "ContributorID");
        assertFalse(violations.isEmpty());
    }

    @Test
    public void invalidContributorID_Null() {
        contributor.setContributorID(null);
        Set<ConstraintViolation<Contributor>> violations = validator.validateProperty(contributor, "ContributorID");
        assertFalse(violations.isEmpty());
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

    // --- Type of Contributor and ContributorID matching tests ---

    @Test
    public void validTypeAndID_Matching() {
        Contributor validRestaurantContributor = new Contributor(
                "restaurant_guru",
                "Georgi Ivanov",
                "georgi.restaurant@gmail.com",
                "MyPass123",
                "0897654321",
                "ul. Rakovski 100, Sofia",
                "93123",
                "RESTAURANT",
                "Fine dining experience"
        );

        assertEquals(TypeOfContributor.RESTAURANT, validRestaurantContributor.getTypeOfContributor());
        assertEquals("93123", validRestaurantContributor.getContributorID());
    }

    @Test
    public void invalidTypeAndID_Mismatch_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Contributor(
                    "wrong_type",
                    "Maria Dimitrova",
                    "maria_grocery@icloud.com",
                    "SecurePass123",
                    "0876543210",
                    "Bulgaria 10, Sofia",
                    "91123", // 91 = CANTEEN
                    "GROCERY_STORE", // mismatch
                    "Selling food"
            );
        });

        assertEquals("Contributor ID is not valid or invalid type of contributor", exception.getMessage());
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
