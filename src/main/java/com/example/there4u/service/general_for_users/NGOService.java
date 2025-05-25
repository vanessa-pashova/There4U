package com.example.there4u.service.general_for_users;

import com.example.there4u.dto.NGOUser.NGODto;
import com.example.there4u.dto.NGOUser.NGOUserEditProfileRequest;
import com.example.there4u.dto.NGOUser.NGOUserRegisterRequest;
import com.example.there4u.model.user.NGOUser;
import com.example.there4u.repository.general_users.NGORepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NGOService {
    private final UserService userService;
    private final NGORepository ngoRepository;

    public NGOService(UserService userService, NGORepository ngoRepository) {
        this.userService = userService;
        this.ngoRepository = ngoRepository;
    }

    public void registerNGO(NGOUser user) {
        this.ngoRepository.save(user);
        log.info("NGO registered successfully, id: {}", user.getId());
    }

    public NGODto createNGO(NGOUserRegisterRequest registerRequest) {
        NGOUser ngoUser = new NGOUser(
                registerRequest.username(),
                registerRequest.name(),
                registerRequest.email(),
                registerRequest.password(),
                registerRequest.phone(),
                registerRequest.address(),
                registerRequest.description()
        );

        userService.encodePassword(ngoUser);

        registerNGO(ngoUser);
        return NGODto.fromEntity(ngoUser);
    }

    public NGODto updateNGOUser(Long id, NGOUserEditProfileRequest ngoUserRequest) {
        NGOUser ngoUser = this.ngoRepository.findById(id).orElse(null);

        if (ngoUser == null) {
            log.error("Attepted to edit NULL NGO User");
            throw new IllegalArgumentException("NGO User cannot be null");
        }

        if (ngoUserRequest.username() != null) {
            ngoUser.setUsername(ngoUserRequest.username());
        }

        if (ngoUserRequest.name() != null) {
            ngoUser.setName(ngoUserRequest.name());
        }

        if (ngoUserRequest.password() != null) {
            ngoUser.setPassword(ngoUserRequest.password());
        }

        if (ngoUserRequest.phoneNumber() != null) {
            ngoUser.setPhone(ngoUserRequest.phoneNumber());
        }

        if (ngoUserRequest.address() != null) {
            ngoUser.setAddress(ngoUserRequest.address());
        }

        if (ngoUserRequest.description() != null) {
            ngoUser.setDescription(ngoUserRequest.description());
        }

        userService.encodePassword(ngoUser);

        ngoRepository.save(ngoUser);
        log.info("NGO updated successfully, id: {}", id);
        return NGODto.fromEntity(ngoUser);
    }

    public void deleteNGO(NGOUser user) {
        if (user == null) {
            log.error("Attempted to delete NULL NGO User");
            throw new IllegalArgumentException("User cannot be null");
        }

        long id = user.getId();
        this.ngoRepository.delete(user);
        log.info("NGO User successfully deleted, id: {}", id);
    }

    public NGOUser findNGOUserById(long id) {
        return ngoRepository.findById(id).orElse(null);
    }

    public NGOUser findNGOUserByUsername(String username) {
        return ngoRepository.findByUsername(username);
    }

    public NGOUser findNGOUserByEmail(String email) {
        return ngoRepository.findByEmail(email);
    }
}
