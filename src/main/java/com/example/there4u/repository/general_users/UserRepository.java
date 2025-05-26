package com.example.there4u.repository.general_users;

import com.example.there4u.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findById(long id);
    Optional<User> findByVerificationCode(String verificationCode);
}
