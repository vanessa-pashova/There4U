package com.example.there4u.models;

import com.example.there4u.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnonymousUserTest {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        lenient().when(userRepository.existsById(anyLong())).thenReturn(false);
        lenient().when(userRepository.existsByUsername(anyString())).thenReturn(false);
    }

    @Test
    void testAnonymousUserWithRepository() {
        AnonymousUser anonymousUser = new AnonymousUser(userRepository);

        assertTrue(anonymousUser.isAnonymous(), "AnonymousUser should always be anonymous.");
        assertEquals("AnonymousUser", anonymousUser.getUsername(), "AnonymousUser should always have username 'AnonymousUser'.");
        assertTrue(anonymousUser.getId() >= 100000 && anonymousUser.getId() <= 999999,
                "Generated ID should be between 100000 and 999999.");
    }

    @Test
    void testAnonymousUserDefaultConstructor() {
        AnonymousUser anonymousUser = new AnonymousUser();

        assertTrue(anonymousUser.isAnonymous(), "AnonymousUser should always be anonymous.");
        assertEquals("AnonymousUser", anonymousUser.getUsername(), "AnonymousUser should always have username 'AnonymousUser'.");
    }

    @Test
    void testSetUsernameDoesNothingForAnonymousUser() {
        AnonymousUser anonymousUser = new AnonymousUser(userRepository);

        anonymousUser.setUsername("NewUser123", userRepository);

        assertEquals("AnonymousUser", anonymousUser.getUsername(),
                "AnonymousUser should still have username 'AnonymousUser' after attempting to change it.");
    }
}
