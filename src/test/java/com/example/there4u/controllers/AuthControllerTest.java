package com.example.there4u.controllers;

import com.example.there4u.models.RegisteredUser;
import com.example.there4u.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthController authController;

    private RegisteredUser testUser;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        testUser = new RegisteredUser(userRepository, "Lando", "Norris", "land0.norr1s@gmail.com",
                "F1driveFaST", "0888232301", "Official F1 Driver @ McLaren");

        testUser.setUsername("lando.mando", userRepository);
        testUser.setPassword(passwordEncoder.encode(testUser.getPassword()));
    }

    // Successful Registration
    @Test
    void testRegisterUser_Success() {
        when(userRepository.existsByEmail(testUser.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(false);
        when(userRepository.save(any(RegisteredUser.class))).thenReturn(testUser);

        ResponseEntity<?> response = authController.registerUser(testUser);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("[ User registered successfully ]", response.getBody());
        verify(userRepository).save(any(RegisteredUser.class));
    }

    // Registration Failure: Missing First Name
    @Test
    void testRegisterUser_MissingFirstName() {
        RegisteredUser invalidUser = null;

        try {
            invalidUser = new RegisteredUser(userRepository, "", "Norris", "land0.norr1s@gmail.com",
                    "F1driveFaST", "0888232301", "Official F1 Driver @ McLaren");
        } catch (IllegalArgumentException e) {
            assertEquals(">! First name cannot be null or empty", e.getMessage());
        }

        assertNull(invalidUser, "User should not be created when first name is missing.");
    }


    // Registration Failure: Missing Last Name
    @Test
    void testRegisterUser_MissingLastName() {
        RegisteredUser invalidUser = null;

        try {
            invalidUser = new RegisteredUser(userRepository, "Lando", "", "land0.norr1s@gmail.com", "F1driveFaST", "0888232301", "Official F1 Driver @ McLaren");
        } catch (IllegalArgumentException e) {
            assertEquals(">! Last name cannot be null or empty", e.getMessage());
        }

        assertNull(invalidUser, "User should not be created when last name is missing.");
    }

    // Registration Failure: Missing Email
    @Test
    void testRegisterUser_MissingEmail() {
        RegisteredUser invalidUser = null;

        try {
            invalidUser = new RegisteredUser(userRepository, "Lando", "Norris", "",
                    "F1driveFaST", "0888232301", "Official F1 Driver @ McLaren");
        } catch (IllegalArgumentException e) {
            assertEquals(">! Email cannot be null or empty", e.getMessage());
        }

        assertNull(invalidUser, "User should not be created when email is missing.");
    }

    // Registration Failure: Missing Password
    @Test
    void testRegisterUser_MissingPassword() {
        RegisteredUser invalidUser = null;

        try {
            invalidUser = new RegisteredUser(userRepository, "Lando", "Norris", "land0.norr1s@gmail.com",
                    "", "0888232301", "Official F1 Driver @ McLaren");
        } catch (IllegalArgumentException e) {
            assertEquals(">! Password cannot be null or empty", e.getMessage());
        }

        assertNull(invalidUser, "User should not be created when password is missing.");
    }

    // Registration Failure: Email Already Exists
    @Test
    void testRegisterUser_EmailAlreadyExists() {
        when(userRepository.existsByEmail(testUser.getEmail())).thenReturn(true);

        ResponseEntity<?> response = authController.registerUser(testUser);

        assertEquals(400, response.getStatusCode().value());
        assertEquals(">! This email address is already in use", response.getBody());
        verify(userRepository, never()).save(any(RegisteredUser.class));
    }

    // Registration Failure: Username Already Exists
    @Test
    void testRegisterUser_UsernameAlreadyExists() {
        when(userRepository.existsByEmail(testUser.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(true);

        ResponseEntity<?> response = authController.registerUser(testUser);

        assertEquals(400, response.getStatusCode().value());
        assertEquals(">! This username is already taken", response.getBody());
        verify(userRepository, never()).save(any(RegisteredUser.class));
    }

    // Successful Login with Email
    @Test
    void testLoginUser_SuccessWithEmail() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(testUser);

        RegisteredUser loginRequest = new RegisteredUser();
        loginRequest.setEmail(testUser.getEmail(), userRepository);
        loginRequest.setPassword("F1driveFaST");

        ResponseEntity<?> response = authController.loginUser(loginRequest);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("[ User logged successfully ]", response.getBody());
    }

    // Successful Login with Username
    @Test
    void testLoginUser_SuccessWithUsername() {
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(testUser);

        RegisteredUser loginRequest = new RegisteredUser();
        loginRequest.setUsername(testUser.getUsername(), userRepository);
        loginRequest.setPassword("F1driveFaST");

        ResponseEntity<?> response = authController.loginUser(loginRequest);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("[ User logged successfully ]", response.getBody());
    }

    // Login Failure: Incorrect Password
    @Test
    void testLoginUser_IncorrectPassword() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(testUser);

        RegisteredUser loginRequest = new RegisteredUser();
        loginRequest.setEmail(testUser.getEmail(), userRepository);
        loginRequest.setPassword("WrongPassword123");

        ResponseEntity<?> response = authController.loginUser(loginRequest);

        assertEquals(400, response.getStatusCode().value());
        assertEquals(">! Incorrect login credentials", response.getBody());
    }

    // Login Failure: User Not Found
    @Test
    void testLoginUser_UserNotFound() {
        lenient().when(userRepository.findByEmail("land0.norr1s@gmail.com")).thenReturn(null);
        lenient().when(userRepository.findByUsername("lando.mando")).thenReturn(null);

        RegisteredUser loginRequest = new RegisteredUser();
        loginRequest.setEmail(testUser.getEmail(), userRepository);
        loginRequest.setPassword("F1driveFaST");

        ResponseEntity<?> response = authController.loginUser(loginRequest);

        assertEquals(400, response.getStatusCode().value());
        assertEquals(">! Incorrect login credentials", response.getBody());
    }
}
