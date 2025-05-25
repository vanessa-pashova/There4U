package com.example.there4u.repository.general_users;

import com.example.there4u.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
    public User findByEmail(String email);
    public User findById(long id);
}
