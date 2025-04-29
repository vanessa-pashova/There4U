package com.example.there4u.service.registration_and_login;

import com.example.there4u.dto.RegularUserDto;
import com.example.there4u.dto.RegularUserRequest;
import com.example.there4u.model.user.RegularUser;
import com.example.there4u.repository.RegularUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RegularUserService {
    private final RegularUserRepository regularUserRepository;

    public RegularUserService(RegularUserRepository regularUserRepository) {
        this.regularUserRepository = regularUserRepository;
    }

    public void registerRegularUser(RegularUser user) {
        this.regularUserRepository.save(user);
        log.info("RegularUser registered successfully, id: {}", user.getId());
    }

    public void editRegularUser(RegularUser user, String target, String newValue) {
        if (user == null) {
            log.error("Attempted to edit NULL Regular User");
            throw new IllegalArgumentException("User cannot be null");
        }

        String action = "";

        switch (target.toLowerCase()) {
            case "username": {
                user.setUsername(newValue);
                action = "username";
                break;
            }

            case "name": {
                user.setName(newValue);
                action = "name";
                break;
            }

            case "password": {
                user.setPassword(newValue);
                action = "password";
                break;
            }

            case "phone number": {
                user.setPhone(newValue);
                action = "phone number";
                break;
            }

            case "address": {
                user.setAddress(newValue);
                action = "address";
                break;
            }

            default: {
                log.warn("Attempted to edit unknown field: {}", target);
                throw new IllegalArgumentException(">! Invalid target field: " + target);
            }
        }

        regularUserRepository.save(user);
        log.info("Regular User successfully edited their {}, id: {}", action, user.getId());
    }

    public RegularUserDto createRegularUser(RegularUserRequest regularUserRequest) {
        RegularUser regularUser = new RegularUser(
                regularUserRequest.username(),
                regularUserRequest.name(),
                regularUserRequest.email(),
                regularUserRequest.password(),
                regularUserRequest.phone(),
                regularUserRequest.address(),
                regularUserRequest.ucn()
        );
        registerRegularUser(regularUser);
        return RegularUserDto.fromEntity(regularUser);
    }


    public void deleteRegularUser(RegularUser user) {
        if (user == null) {
            log.error("Attempted to delete NULL User");
            throw new IllegalArgumentException("User cannot be null");
        }

        long id = user.getId();
        this.regularUserRepository.delete(user);
        log.info("Regular User successfully deleted, id: {}", id);
    }
}
