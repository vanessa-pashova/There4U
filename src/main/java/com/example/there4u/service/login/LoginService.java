package com.example.there4u.service.login;

import com.example.there4u.dto.login.LoginRequest;
import com.example.there4u.dto.login.LoginResponse;
import com.example.there4u.model.user.Contributor;
import com.example.there4u.model.user.NGOUser;
import com.example.there4u.model.user.RegularUser;
import com.example.there4u.model.user.User;
import com.example.there4u.repository.general_users.ContributorRepository;
import com.example.there4u.repository.general_users.NGORepository;
import com.example.there4u.repository.general_users.RegularUserRepository;
import com.example.there4u.repository.general_users.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@Service
public class LoginService {

    // Repositories for each type of user
    private final UserRepository userRepository;
    private final RegularUserRepository regularUserRepository;
    private final NGORepository ngoRepository;
    private final ContributorRepository contributorRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Extracts the password from the user object based on its actual type.
     * Throws an exception if the type is unsupported.
     */
    private String extractPassword(Object user) {
        if (user instanceof RegularUser u)  return u.getPassword();
        if (user instanceof NGOUser u)      return u.getPassword();
        if (user instanceof Contributor u)  return u.getPassword();

        throw new IllegalArgumentException("Unsupported user type");
    }

    /**
     * Extracts the username from the user object based on its actual type.
     * Returns "unknown" if type is not recognized (for safer logging).
     */
    private String extractUsername(Object user) {
        if (user instanceof RegularUser u)  return u.getUsername();
        if (user instanceof NGOUser u)      return u.getUsername();
        if (user instanceof Contributor u)  return u.getUsername();

        return "unknown"; // instead of throwing, fallback is used for logs
    }

    /**
     * Extracts the ID from the user object based on its actual type.
     * Throws an exception if the type is unsupported.
     */
    private Long extractId(Object user) {
        if (user instanceof RegularUser u)  return u.getId();
        if (user instanceof NGOUser u)      return u.getId();
        if (user instanceof Contributor u)  return u.getId();

        throw new IllegalArgumentException("Unsupported user type");
    }

    /**
     * Attempts to authenticate a user by checking both username and email.
     * If the user is found and password matches, returns a LoginResponse.
     *
     * @param findByUsername function that searches for user by username
     * @param findByEmail function that searches for user by email
     * @param loginRequest login data with email/username and password
     * @param userType string label for user type (for logging)
     * @return Optional containing LoginResponse on success, or empty if user not found
     */
    /*private <T> Optional<LoginResponse> tryLogin(Supplier<T> findByUsername,
                                                 Supplier<T> findByEmail,
                                                 LoginRequest loginRequest,
                                                 String userType) {
        // Try finding the user by username first
        T user = findByUsername.get();
        if (user == null) {
            user = findByEmail.get();   // Then try by email
        }

        if (user == null) {
            return Optional.empty();
        }

        String actualPassword = extractPassword(user);
        String username = extractUsername(user);
        Long id = extractId(user);

        // Check if password matches
        if (!actualPassword.equals(loginRequest.password())) {
            log.error("{} {} inserted wrong password", userType, username != null ? username : "unknown");
            throw new IllegalArgumentException("Wrong password for " + userType + " " + (username != null ? username : ""));
        }

        // Success
        log.info("{} {} logged in successfully", userType, username);
        return Optional.of(new LoginResponse(userType, id, username));
    }*/

    /**
     * Constructor with all user repositories injected.
     */
    public LoginService(UserRepository userRepository, RegularUserRepository regularUserRepository,
                        NGORepository ngoRepository,
                        ContributorRepository contributorRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.regularUserRepository = regularUserRepository;
        this.ngoRepository = ngoRepository;
        this.contributorRepository = contributorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Main login method that tries to authenticate the user across
     * the three user types: RegularUser, NGOUser, Contributor.
     * Falls back through each type if not found.
     *
     * @param loginRequest the credentials used for login
     * @return LoginResponse on success
     * @throws IllegalArgumentException if a user is not found or password is incorrect
     */
    /*public LoginResponse login(LoginRequest loginRequest) {
        return tryLogin(
                () -> regularUserRepository.findByUsername(loginRequest.username()),
                () -> regularUserRepository.findByEmail(loginRequest.email()),
                loginRequest,
                "REGULAR_USER"
        ).orElseGet(() ->
                tryLogin(
                        () -> ngoRepository.findByUsername(loginRequest.username()),
                        () -> ngoRepository.findByEmail(loginRequest.email()),
                        loginRequest,
                        "NGO"
                ).orElseGet(() ->
                        tryLogin(
                                () -> contributorRepository.findContributorByUsername(loginRequest.username()),
                                () -> contributorRepository.findContributorByEmail(loginRequest.email()),
                                loginRequest,
                                "CONTRIBUTOR"
                        ).orElseThrow(() -> {
                            log.warn("User with email '{}' or username '{}' not found",
                                    loginRequest.email(), loginRequest.username());
                            return new IllegalArgumentException("User not found");
                        })
                )
        );
    }*/

    public User authenticate(LoginRequest loginRequest) {
        User user;
        String username = loginRequest.username();
        String email = loginRequest.email();
        String hashedPassword = passwordEncoder.encode(loginRequest.password());
        if(username != null) {
            user = userRepository.findByUsername(username);
        }
        else {
            user = userRepository.findByEmail(email);
        }
        if(user == null) {
            log.warn("User with email '{}' or username '{}' not found", email, username);
            throw new EntityNotFoundException("Wrong email/username.");
        }
        if (passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            return user;
        }
        log.warn("User with email '{}' or username '{}' does not match password: {}", email, username, loginRequest.password());
        throw new EntityNotFoundException("Wrong email/username or password.");
    }
}
