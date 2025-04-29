package com.example.there4u.repository;

import com.example.there4u.model.user.NGOUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NGORepository extends JpaRepository<NGOUser, String> {

}
