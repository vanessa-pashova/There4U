package com.example.there4u.repository.general_users;

import com.example.there4u.model.user.NGOUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NGORepository extends JpaRepository<NGOUser, Long> {
    NGOUser findByUsername(String username);
    NGOUser findByEmail(String email);
}
