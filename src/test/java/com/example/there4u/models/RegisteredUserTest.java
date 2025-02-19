package com.example.there4u.models;

import com.example.there4u.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisteredUserTest {

    @Mock
    private UserRepository userRepository;

    private RegisteredUser user;

    @BeforeEach
    void setUp() {
        user = new RegisteredUser(userRepository, "John", "Doe", "john.doe@gmail.com", "StrongP@ss1", "0871234567", "Hello, I am John!");
    }

    @Test
    void testValidUserCreation() {
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@gmail.com", user.getEmail());
        assertEquals("StrongP@ss1", user.getPassword());
        assertEquals("0871234567", user.getPhoneNumber());
        assertEquals("Hello, I am John!", user.getBio());
    }

    @Test
    void testInvalidFirstNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> user.setFirstName("J"));
        assertThrows(IllegalArgumentException.class, () -> user.setFirstName("JohndoeJohndoeJohndoe"));
        assertThrows(IllegalArgumentException.class, () -> user.setFirstName("John123"));
    }

    @Test
    void testInvalidLastNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> user.setLastName("D"));
        assertThrows(IllegalArgumentException.class, () -> user.setLastName("DoeDoeDoeDoeDoeDoe"));
        assertThrows(IllegalArgumentException.class, () -> user.setLastName("Doe123"));
    }

    @Test
    void testInvalidEmailThrowsException() {
        when(userRepository.existsByEmail("usedemail@gmail.com")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> user.setEmail(null, userRepository));
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("invalidemail", userRepository));
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("short@gm.com", userRepository));
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("averyverylongemailaddress@someprovider.com", userRepository));
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("user@invalid.com", userRepository));
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("usedemail@gmail.com", userRepository));

        verify(userRepository, times(1)).existsByEmail("usedemail@gmail.com");
    }

    @Test
    void testInvalidPasswordThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> user.setPassword("short"));
        assertThrows(IllegalArgumentException.class, () -> user.setPassword("alllowercase"));
        assertThrows(IllegalArgumentException.class, () -> user.setPassword("ALLUPPERCASE"));
        assertThrows(IllegalArgumentException.class, () -> user.setPassword("12345678"));
        assertThrows(IllegalArgumentException.class, () -> user.setPassword("NoNumbersHere"));
    }

    @Test
    void testInvalidPhoneNumberThrowsException() {
        when(userRepository.existsPhoneNumber("0871234567")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> user.setPhoneNumber("088", userRepository));
        assertThrows(IllegalArgumentException.class, () -> user.setPhoneNumber("089123456", userRepository));
        assertThrows(IllegalArgumentException.class, () -> user.setPhoneNumber("08912345678", userRepository));
        assertThrows(IllegalArgumentException.class, () -> user.setPhoneNumber("08912A3456", userRepository));
        assertThrows(IllegalArgumentException.class, () -> user.setPhoneNumber("0871234567", userRepository));

        // The `setPhoneNumber` method is called twice – once in the constructor and once in the test
        // There is a redundant check inside `setPhoneNumber`, leading to multiple calls to `userRepository.existsPhoneNumber(phoneNumber)`
        verify(userRepository, times(2)).existsPhoneNumber("0871234567");
    }

    @Test
    void testInvalidBioThrowsException() {
        String longBio = "a".repeat(513);
        assertThrows(IllegalArgumentException.class, () -> user.setBio(longBio));
    }

    @Test
    void testValidBio() {
        String validBio = "a".repeat(512);
        assertDoesNotThrow(() -> user.setBio(validBio));
        assertEquals(validBio, user.getBio());
    }

    @Test
    void testLevelProgress() {
        assertNotNull(user.getLevel());
        user.addLevelProgress(0.1);
        assertEquals(0.1, user.getLevel().getProgress());
    }
}
