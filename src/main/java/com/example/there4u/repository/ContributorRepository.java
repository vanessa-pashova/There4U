package com.example.there4u.repository;

import com.example.there4u.model.user.Contributor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContributorRepository extends JpaRepository<Contributor, String> {
    Contributor findContributorById(long id);
    Contributor findContributorByUsername(String username);
    Contributor findContributorByEmail(String email);
}
