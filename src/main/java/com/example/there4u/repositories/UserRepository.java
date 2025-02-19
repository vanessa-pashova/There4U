package com.example.there4u.repositories;

import com.example.there4u.models.RegisteredUser;
import com.example.there4u.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsById(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsPhoneNumber(String phoneNumber);

    RegisteredUser findByEmail(String email);
}
