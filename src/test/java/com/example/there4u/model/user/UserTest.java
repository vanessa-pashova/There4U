package com.example.there4u.model.user;

import org.junit.jupiter.api.Test;
import jakarta.validation.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private static class TestUser extends User {
        public static long idCounter = 0;

        @Override
        protected long generateId() {
            return idCounter++;
        }

        public TestUser(String username, String name, String email, String password, String phone, String address) {
            super(username, name, email, password, phone, address);
            this.id = generateId();
        }
    }

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private TestUser user = new TestUser(
            "papi_chulo",
            "Petar Georgiev",
            "papi@icloud.com",
            "Password1",
            "0882340592",
            "bul. James Bourchier"
    );

    // --- Username tests ---

    @Test
    public void validUsername() {
        user.username = "papi_chulo";
        Set<ConstraintViolation<TestUser>> violations = validator.validateProperty(user, "username");
        assertTrue(violations.isEmpty());
    }

    @Test
    public void invalidUsername_SpecialCharacters() {
        user.username = "papi.chulo!";
        Set<ConstraintViolation<TestUser>> violations = validator.validateProperty(user, "username");
        assertFalse(violations.isEmpty());
    }

    @Test
    public void invalidUsername_TooShort() {
        user.username = "abc";
        Set<ConstraintViolation<TestUser>> violations = validator.validateProperty(user, "username");
        assertFalse(violations.isEmpty());
    }

    @Test
    public void invalidUsername_TooLong() {
        user.username = "this_username_is_way_too_long";
        Set<ConstraintViolation<TestUser>> violations = validator.validateProperty(user, "username");
        assertFalse(violations.isEmpty());
    }

    // --- Name tests ---

    @Test
    public void validName() {
        user.name = "Petar Georgiev";
        Set<ConstraintViolation<TestUser>> violations = validator.validateProperty(user, "name");
        assertTrue(violations.isEmpty());
    }

    @Test
    public void invalidName_NoCapitalLetter() {
        user.name = "petar georgiev";
        Set<ConstraintViolation<TestUser>> violations = validator.validateProperty(user, "name");
        assertFalse(violations.isEmpty());
    }

    @Test
    public void invalidName_NumbersInName() {
        user.name = "Petar123 Georgiev";
        Set<ConstraintViolation<TestUser>> violations = validator.validateProperty(user, "name");
        assertFalse(violations.isEmpty());
    }

    // --- Email tests ---

    @Test
    public void validEmail() {
        user.email = "papi@icloud.com";
        Set<ConstraintViolation<TestUser>> violations = validator.validateProperty(user, "email");
        assertTrue(violations.isEmpty());
    }

    @Test
    public void invalidEmail_NoAtSymbol() {
        user.email = "papiicloud.com";
        Set<ConstraintViolation<TestUser>> violations = validator.validateProperty(user, "email");
        assertFalse(violations.isEmpty());
    }

    @Test
    public void invalidEmail_BadDomain() {
        user.email = "papi@icloud";
        Set<ConstraintViolation<TestUser>> violations = validator.validateProperty(user, "email");
        assertFalse(violations.isEmpty());
    }

    // --- Password tests ---

    @Test
    public void validPassword() {
        user.password = "Password1";
        Set<ConstraintViolation<TestUser>> violations = validator.validateProperty(user, "password");
        assertTrue(violations.isEmpty());
    }

    @Test
    public void invalidPassword_NoUppercase() {
        user.password = "password1";
        Set<ConstraintViolation<TestUser>> violations = validator.validateProperty(user, "password");
        assertFalse(violations.isEmpty());
    }

    @Test
    public void invalidPassword_NoLowercase() {
        user.password = "PASSWORD1";
        Set<ConstraintViolation<TestUser>> violations = validator.validateProperty(user, "password");
        assertFalse(violations.isEmpty());
    }

    @Test
    public void invalidPassword_NoDigit() {
        user.password = "Password";
        Set<ConstraintViolation<TestUser>> violations = validator.validateProperty(user, "password");
        assertFalse(violations.isEmpty());
    }

    @Test
    public void invalidPassword_TooShort() {
        user.password = "Pas1";
        Set<ConstraintViolation<TestUser>> violations = validator.validateProperty(user, "password");
        assertFalse(violations.isEmpty());
    }

    // --- Phone tests ---

    @Test
    public void validPhone() {
        Set<String> validPhones = Set.of(
                "27771300",
                "0874341011", "0884341011", "0894341011",
                "00359874341011", "00359884341011", "00359894341011",
                "+359874341011", "+359884341011", "+359894341011"
        );

        for (String phone : validPhones) {
            user.phone = phone;
            Set<ConstraintViolation<TestUser>> violations = validator.validateProperty(user, "phone");
            assertTrue(violations.isEmpty(), "Expected valid phone but got violation for: " + phone);
        }
    }

    @Test
    public void invalidPhone_WrongPrefix() {
        user.phone = "0864341011";
        Set<ConstraintViolation<TestUser>> violations = validator.validateProperty(user, "phone");
        assertFalse(violations.isEmpty());
    }

    @Test
    public void invalidPhone_TooShort() {
        user.phone = "0884341";
        Set<ConstraintViolation<TestUser>> violations = validator.validateProperty(user, "phone");
        assertFalse(violations.isEmpty());
    }

    @Test
    public void invalidPhone_TooLong() {
        user.phone = "088434101112345";
        Set<ConstraintViolation<TestUser>> violations = validator.validateProperty(user, "phone");
        assertFalse(violations.isEmpty());
    }

    // --- Address tests (only setAddress() can validate) ---

    @Test
    public void validAddress() {
        user.setAddress("Атанас Манчев 21, София");
        assertEquals("Атанас Манчев 21, София", user.getAddress());

        user.setAddress("Dyakon Ignatiy 5, Sofia");
        assertEquals("Dyakon Ignatiy 5, Sofia", user.getAddress());
    }

    @Test
    public void invalidAddress() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            user.setAddress("Баба Спуза 123, Варна");
        });

        assertEquals(">! Invalid address [setAddress(), User]", e.getMessage());
    }
}
