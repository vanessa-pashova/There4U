package com.example.there4u.model.user;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private static class TestUser extends User {
        public TestUser(String name, String email, String password, String phone, String address, TypeOfUser typeOfUser) {
            super(name, email, password, phone, address, typeOfUser);
        }
    }

    private TestUser nullTestUser,
                     user = new TestUser("Petar Georgiev Ivanov", "papi@icloud.com", "PastaLover07", "0882340592", "bul. James Bourchier", TypeOfUser.REGULAR_USER);

    @Test
    public void validUserCreation() {
        nullTestUser = new TestUser("Petar Georgiev Ivanov", "papi@icloud.com", "PastaLover07", "0882340592", "bul. James Bourchier", TypeOfUser.REGULAR_USER);
        assertEquals("Petar Georgiev Ivanov", nullTestUser.getName());
        assertEquals("papi@icloud.com", nullTestUser.getEmail());
        assertEquals("PastaLover07", nullTestUser.getPassword());
        assertEquals("0882340592", nullTestUser.getPhone());
        assertEquals("bul. James Bourchier", nullTestUser.getAddress());
        assertEquals(TypeOfUser.REGULAR_USER, nullTestUser.getTypeOfUser());
    }

    @Test
    public void invalidUserName_SmallLetters() {
        user.setName("petar georgiev ivanov");
        assertEquals("Petar Georgiev Ivanov", user.getName());
    }

    @Test
    public void invalidUserName_TooManySpaces() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
           user.setName("   Petar        Georgiev Ivanov    ");
        });

        assertEquals(">! Name contains multiple spaces in a row [validateName(), NameValidator]", e.getMessage());
    }

    @Test
    public void invalidUserName_NotAlphabeticSymbols() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            user.setName("Petar12 Georgiev560 Ivanov4");
        });

        assertEquals(">! Name must only contain letters [validateName(), NameValidator]", e.getMessage());
    }

    @Test
    public void validUserEmail() {
        String emailName = "papi";
        Set<String> allowedDomains = Set.of("@gmail.com", "@abv.bg", "@yahoo.com", "@mail.com", "@icloud.com", "@outlook.com", "@hotmail.com");

        for(String allowedDomain : allowedDomains) {
            String email = emailName + allowedDomain;
            user.setEmail(email);

            assertEquals(email, user.getEmail());
        }
    }

    @Test
    public void validUserEmail_WithDigits() {
        user.setEmail("papi012@icloud.com");
        assertEquals("papi012@icloud.com", user.getEmail());
    }

    @Test
    public void validUserEmail_DomainChange() {
        user.setEmail("papi@yahoo.com");
        assertEquals("papi@yahoo.com", user.getEmail());
    }

    @Test
    public void invalidUserEmail_CapitalLetters() {
        user.setEmail("Pesho_Be@icloud.com");
        assertEquals("pesho_be@icloud.com", user.getEmail());
    }

    @Test
    public void invalidUserEmail_InvalidEmailDomain() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            user.setEmail("papi@gmx.de");
        });

        assertEquals(">! Invalid email address [setEmail(), User]", e.getMessage());
    }

    @Test
    public void validUserPassword() {
        user.setPassword("LasagnaLover199!");
        assertEquals("LasagnaLover199!", user.getPassword());
    }

    @Test
    public void invalidUserPassword_ShortPassword() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            user.setPassword("Pasta07");
        });

        assertEquals(">! Password must contain at least 8 symbols [validatePassword(), PasswordValidator]", e.getMessage());
    }

    @Test
    public void invalidUserPassword_NoCapitalLetter() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            user.setPassword("pastalover07");
        });

        assertEquals(">! Password must contain at least one: capital letter, small letter and a digit [validatePassword(), PasswordValidator]", e.getMessage());
    }

    @Test
    public void invalidUserPassword_NoSmallLetter() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            user.setPassword("PASTALOVER07");
        });

        assertEquals(">! Password must contain at least one: capital letter, small letter and a digit [validatePassword(), PasswordValidator]", e.getMessage());
    }

    @Test
    public void invalidUserPassword_NoDigit() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            user.setPassword("PastaLover");
        });

        assertEquals(">! Password must contain at least one: capital letter, small letter and a digit [validatePassword(), PasswordValidator]", e.getMessage());
    }

    @Test
    public void validUserPhoneNumber() {
        Set<String> examples = Set.of("27771300",
                                    "0874341011", "0884341011", "0894341011",
                                    "00359874341011", "00359884341011", "00359894341011",
                                    "+359874341011", "+359884341011", "+359894341011");

        for(String example : examples) {
            user.setPhone(example);
            assertEquals(example, user.getPhone());
        }
    }

    @Test
    public void invalidUserPhoneNumber_InvalidLandNumber() {
        IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> {
            user.setPhone("37771300");
        });

        assertEquals(">! Invalid phone number [setPhone(), User]", e1.getMessage());

        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            user.setPhone("271300");
        });

        assertEquals(">! Invalid phone number [setPhone(), User]", e2.getMessage());
    }

    @Test
    public void invalidUserPhoneNumber_InvalidMobileNumber() {
        IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> {
            user.setPhone("0864341011");
        });

        assertEquals(">! Invalid phone number [setPhone(), User]", e1.getMessage());

        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            user.setPhone("369864341011");
        });

        assertEquals(">! Invalid phone number [setPhone(), User]", e2.getMessage());

        IllegalArgumentException e3 = assertThrows(IllegalArgumentException.class, () -> {
            user.setPhone("088434101");
        });

        assertEquals(">! Invalid phone number [setPhone(), User]", e3.getMessage());
    }

    @Test
    public void validUserAddress() {
        user.setAddress("Атанас Манчев 21, София");
        assertEquals("Атанас Манчев 21, София", user.getAddress());

        user.setAddress("Dyakon Ignatiy 5, Sofia");
        assertEquals("Dyakon Ignatiy 5, Sofia", user.getAddress());
    }

    @Test
    public void invalidUserAddress_InvalidAddress() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            user.setAddress("Баба Спуза 123, Варна");
        });

        assertEquals(">! Invalid address [setAddress(), User]", e.getMessage());
    }
}
