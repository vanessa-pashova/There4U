package com.example.there4u.service.general_for_users;

import com.example.there4u.model.user.Contributor;
import com.example.there4u.model.user.NGOUser;
import com.example.there4u.model.user.RegularUser;
import com.example.there4u.model.user.User;
import com.example.there4u.repository.general_users.ContributorRepository;
import com.example.there4u.repository.general_users.NGORepository;
import com.example.there4u.repository.general_users.RegularUserRepository;
import com.example.there4u.repository.general_users.UserRepository;
import com.example.there4u.service.email.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RegularUserRepository regularUserRepository;
    private final NGORepository nGORepository;
    private final ContributorRepository contributorRepository;
    private final EmailService emailService;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, RegularUserRepository regularUserRepository, NGORepository nGORepository, ContributorRepository contributorRepository, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.regularUserRepository = regularUserRepository;
        this.nGORepository = nGORepository;
        this.contributorRepository = contributorRepository;
        this.emailService = emailService;
    }

    public void encodePassword(User user) {
        String rawPassword = user.getPassword();
        String hashedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    public void initiateEmailVerification(User user) {
        String code = UUID.randomUUID().toString();
        user.setVerificationCode(code);
        user.setVerificationExpiration(LocalDateTime.now().plusMinutes(30));
        user.setEnabled(false);

        if(user instanceof RegularUser regularUser) {
            regularUserRepository.save(regularUser);
        } else if (user instanceof NGOUser ngoUser) {
            nGORepository.save(ngoUser);
        } else if (user instanceof Contributor contributor) {
            contributorRepository.save(contributor);
        }

        String link = "http://localhost:8080/api/auth/verify?code=" + code;
        emailService.sendMail(user.getEmail(), emailService.buildVerificationEmail(user.getName(), user.getEmail(), link));
    }
}
