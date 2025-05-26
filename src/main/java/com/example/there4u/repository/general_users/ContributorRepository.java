package com.example.there4u.repository.general_users;

import com.example.there4u.model.user.Contributor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContributorRepository extends JpaRepository<Contributor, Long> {
    Contributor findContributorById(long id);
    Contributor findContributorByUsername(String username);
    Contributor findContributorByEmail(String email);

    Optional<Contributor> findByVerificationCode(String code);
}
