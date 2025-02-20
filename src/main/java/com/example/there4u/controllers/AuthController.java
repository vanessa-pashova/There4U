package com.example.there4u.controllers;

import com.example.there4u.models.RegisteredUser;
import com.example.there4u.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthController(UserRepository userRepository) {
        //Initialization
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    // User registration
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisteredUser user) {
        try{
            // Check if first name, last name, email, and password are provided
            if (user.getFirstName() == null || user.getFirstName().trim().isEmpty())
                return ResponseEntity.badRequest().body(">! First name cannot be null or empty");

            if (user.getLastName() == null || user.getLastName().trim().isEmpty())
                return ResponseEntity.badRequest().body(">! Last name cannot be null or empty");

            if (user.getEmail() == null || user.getEmail().trim().isEmpty())
                return ResponseEntity.badRequest().body(">! Email cannot be null or empty");

            if (user.getPassword() == null || user.getPassword().trim().isEmpty())
                return ResponseEntity.badRequest().body(">! Password cannot be null or empty");

            // Check if email or username is already taken
            if (userRepository.existsByEmail(user.getEmail()))
                return ResponseEntity.badRequest().body(">! This email address is already in use");

            if (userRepository.existsByUsername(user.getUsername()))
                return ResponseEntity.badRequest().body(">! This username is already taken");

            // Hash the password before saving to the database
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            // Save the new user to the database
            userRepository.save(user);
            return ResponseEntity.ok("[ User registered successfully ]");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // User login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody RegisteredUser loginRequest) {
        // Try finding the user by email
        RegisteredUser user = userRepository.findByEmail(loginRequest.getEmail());

        // If user is not found by email, try finding by username
        if (user == null)
            user = userRepository.findByUsername(loginRequest.getUsername()); // Using email field for username input

        // If user is still not found or password is incorrect
        if (user == null || !bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            return ResponseEntity.badRequest().body(">! Incorrect login credentials");

        return ResponseEntity.ok("[ User logged successfully ]");
    }
}