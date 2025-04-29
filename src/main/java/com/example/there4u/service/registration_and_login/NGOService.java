package com.example.there4u.service.registration_and_login;

import com.example.there4u.model.user.NGOUser;
import com.example.there4u.repository.NGORepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NGOService {
    private final NGORepository ngoRepository;

    public NGOService(NGORepository ngoRepository) {
        this.ngoRepository = ngoRepository;
    }

    public void registerNGO(NGOUser user) {
        this.ngoRepository.save(user);
        log.info("NGO registered successfully, id: {}", user.getNGOid());
    }

    public void editNGO(NGOUser user, String target, String newValue) {
        if (user == null) {
            log.error("Attempted to edit NULL NGO User");
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

            case "description": {
                user.setDescription(newValue);
                action = "description";
                break;
            }

            default: {
                log.warn("Attempted to edit unknown field: {}", target);
                throw new IllegalArgumentException("Invalid target field: " + target);
            }
        }

        ngoRepository.save(user);
        log.info("NGO User successfully edited their {}, id: {}", action, user.getNGOid());
    }


    public void deleteNGO(NGOUser user) {
        if(user == null) {
            log.error("Attempted to delete NULL NGO User");
            throw new IllegalArgumentException("User cannot be null");
        }

        String id = user.getNGOid();
        this.ngoRepository.delete(user);
        log.info("NGO User successfully deleted, id: {}", id);
    }
}
