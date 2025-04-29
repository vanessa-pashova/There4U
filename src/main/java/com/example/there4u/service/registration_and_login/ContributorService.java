package com.example.there4u.service.registration_and_login;

import com.example.there4u.model.user.Contributor;
import com.example.there4u.repository.ContributorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContributorService {
    private final ContributorRepository contributorRepository;

    public ContributorService(ContributorRepository contributorRepository) {
        this.contributorRepository = contributorRepository;
    }

    public void registerContributor(Contributor contributor) {
        contributorRepository.save(contributor);
        log.info("Contributor registered successfully, id: {}", contributor.getId());
    }

    public void editContributor(Contributor contributor, String target, String newValue) {
        if (contributor == null) {
            log.error("Attempted to edit NULL contributor");
            throw new IllegalArgumentException("Contributor cannot be null");
        }

        String action = "";

        switch (target) {
            case "username": {
                contributor.setUsername(newValue);
                action = "username";
                break;
            }

            case "name": {
                contributor.setName(newValue);
                action = "name";
                break;
            }

            case "password": {
                contributor.setPassword(newValue);
                action = "password";
                break;
            }

            case "phone number": {
                contributor.setPhone(newValue);
                action = "phone number";
                break;
            }

            case "address": {
                contributor.setAddress(newValue);
                action = "address";
                break;
            }

            case "description": {
                contributor.setDescription(newValue);
                action = "description";
                break;
            }

            default: {
                log.warn("Attempted to edit unknown target: {}", target);
                throw new IllegalArgumentException("Invalid target field: " + target);
            }
        }

        contributorRepository.save(contributor);
        log.info("Contributor edited successfully their {}, id: {}", action, contributor.getId());
    }

    public void deleteContributor(Contributor contributor) {
        if (contributor == null) {
            log.error("Attempted to delete NULL contributor");
            throw new IllegalArgumentException("Contributor cannot be null");
        }

        long id = contributor.getId();
        contributorRepository.delete(contributor);
        log.info("Contributor deleted successfully, id: {}", id);
    }
}
