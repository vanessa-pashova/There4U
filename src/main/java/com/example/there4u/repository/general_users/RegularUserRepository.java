package com.example.there4u.repository.general_users;

import com.example.there4u.model.user.RegularUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegularUserRepository extends JpaRepository<RegularUser, Long> {
    RegularUser findByUsername(String username);
    RegularUser findByEmail(String email);

    Optional<RegularUser> findByVerificationCode(String code);
}
