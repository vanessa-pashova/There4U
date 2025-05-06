package com.example.there4u.repository;

import com.example.there4u.model.user.RegularUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegularUserRepository extends JpaRepository<RegularUser, Long> {
    RegularUser findByUsername(String username);
    RegularUser findByEmail(String email);
}
