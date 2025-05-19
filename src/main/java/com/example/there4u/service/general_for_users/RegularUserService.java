package com.example.there4u.service.general_for_users;

import com.example.there4u.dto.RegularUser.RegularUserDto;
import com.example.there4u.dto.RegularUser.RegularUserEditProfileRequest;
import com.example.there4u.dto.RegularUser.RegularUserRegisterRequest;
import com.example.there4u.model.user.RegularUser;
import com.example.there4u.repository.general_users.RegularUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RegularUserService {
    private final UserService userService;
    private final RegularUserRepository regularUserRepository;

    public RegularUserService(UserService userService ,RegularUserRepository regularUserRepository) {
        this.userService = userService;
        this.regularUserRepository = regularUserRepository;
    }

    public void registerRegularUser(RegularUser user) {
        this.regularUserRepository.save(user);
        log.info("RegularUser registered successfully, id: {}", user.getId());
    }

    public RegularUserDto createRegularUser(RegularUserRegisterRequest regularUserRequest) {
        RegularUser regularUser = new RegularUser(
                regularUserRequest.username(),
                regularUserRequest.name(),
                regularUserRequest.email(),
                regularUserRequest.password(),
                regularUserRequest.phone(),
                regularUserRequest.address(),
                regularUserRequest.ucn()
        );

        userService.encodePassword(regularUser);

        registerRegularUser(regularUser);
        return RegularUserDto.fromEntity(regularUser);
    }

    public RegularUserDto updateRegularUser(Long id, RegularUserEditProfileRequest regularUserRequest) {
        RegularUser regularUser = findRegularUserById(id);

        if (regularUser == null) {
            log.error("Attempted to edit NULL Regular User");
            throw new IllegalArgumentException("Regular User cannot be null");
        }

        if (regularUserRequest.username() != null) {
            regularUser.setUsername(regularUserRequest.username());
        }

        if (regularUserRequest.name() != null) {
            regularUser.setName(regularUserRequest.name());
        }

        if (regularUserRequest.password() != null) {
            regularUser.setPassword(regularUserRequest.password());
        }

        if (regularUserRequest.phoneNumber() != null) {
            regularUser.setPhone(regularUserRequest.phoneNumber());
        }

        if (regularUserRequest.address() != null) {
            regularUser.setAddress(regularUserRequest.address());
        }

        userService.encodePassword(regularUser);

        regularUserRepository.save(regularUser);
        log.info("RegularUser updated successfully, id: {}", regularUser.getId());
        return RegularUserDto.fromEntity(regularUser);
    }

    public void deleteRegularUser(RegularUser user) {
        if (user == null) {
            log.error("Attempted to delete NULL User");
            throw new IllegalArgumentException("Regular User cannot be null");
        }

        long id = user.getId();
        this.regularUserRepository.delete(user);
        log.info("Regular User successfully deleted, id: {}", id);
    }

    public RegularUser findRegularUserById(long id) {
        return regularUserRepository.findById(id).orElse(null);
    }

    public RegularUser findRegularUserByUsername(String username) {
        return regularUserRepository.findByUsername(username);
    }

    public RegularUser findRegularUserByEmail(String email) {
        return regularUserRepository.findByEmail(email);
    }
}
