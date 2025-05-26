package com.example.there4u.controller.email_verification;

import com.example.there4u.model.user.Contributor;
import com.example.there4u.model.user.NGOUser;
import com.example.there4u.model.user.RegularUser;
import com.example.there4u.model.user.User;
import com.example.there4u.repository.general_users.ContributorRepository;
import com.example.there4u.repository.general_users.NGORepository;
import com.example.there4u.repository.general_users.RegularUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth/verify")
public class VerificationController {

    private final ContributorRepository contributorRepository;
    private final NGORepository ngoRepository;
    private final RegularUserRepository regularUserRepository;

    @GetMapping
    public ResponseEntity<String> verifyUser(@RequestParam("code") String code) {
        Optional<User> optionalUser = Stream.<Optional<? extends User>>of(
                        contributorRepository.findByVerificationCode(code),
                        ngoRepository.findByVerificationCode(code),
                        regularUserRepository.findByVerificationCode(code)
                ).filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> (User) user)
                .findFirst();

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid or already used verification code.");
        }

        User user = optionalUser.get();

        if (user.getVerificationExpiration() == null || user.getVerificationExpiration().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("The verification code has expired.");
        }

        user.setEnabled(true);
        user.setVerificationCode(null);
        user.setVerificationExpiration(null);

        if (user instanceof Contributor contributor) contributorRepository.save(contributor);
        if (user instanceof NGOUser ngo) ngoRepository.save(ngo);
        if (user instanceof RegularUser regularUser) regularUserRepository.save(regularUser);

        return ResponseEntity.ok("Email successfully verified. You can now log in.");
    }
}
