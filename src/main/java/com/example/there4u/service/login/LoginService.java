package com.example.there4u.service.login;

import com.example.there4u.dto.login.LoginRequest;
import com.example.there4u.dto.login.LoginResponse;
import com.example.there4u.model.user.Contributor;
import com.example.there4u.model.user.NGOUser;
import com.example.there4u.model.user.RegularUser;
import com.example.there4u.repository.ContributorRepository;
import com.example.there4u.repository.NGORepository;
import com.example.there4u.repository.RegularUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@Service
public class LoginService {

    // Repositories for each type of user
    private final RegularUserRepository regularUserRepository;
    private final NGORepository ngoRepository;
    private final ContributorRepository contributorRepository;

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
    private <T> Optional<LoginResponse> tryLogin(Supplier<T> findByUsername,
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
    }

    /**
     * Constructor with all user repositories injected.
     */
    public LoginService(RegularUserRepository regularUserRepository,
                        NGORepository ngoRepository,
                        ContributorRepository contributorRepository) {
        this.regularUserRepository = regularUserRepository;
        this.ngoRepository = ngoRepository;
        this.contributorRepository = contributorRepository;
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
    public LoginResponse login(LoginRequest loginRequest) {
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
    }
}
