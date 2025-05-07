package com.example.there4u.service.general_for_users;

import com.example.there4u.dto.Contributor.ContributorDto;
import com.example.there4u.dto.Contributor.ContributorEditProfileRequest;
import com.example.there4u.dto.Contributor.ContributorRegisterRequest;
import com.example.there4u.model.user.Contributor;
import com.example.there4u.repository.general_users.ContributorRepository;
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

    public ContributorDto createContributor(ContributorRegisterRequest registerRequest) {
        Contributor contributor = new Contributor(
                registerRequest.username(),
                registerRequest.name(),
                registerRequest.email(),
                registerRequest.password(),
                registerRequest.phone(),
                registerRequest.address(),
                registerRequest.typeOfUser(),
                registerRequest.description()
        );

        registerContributor(contributor);
        return ContributorDto.fromEntity(contributor);
    }

    public ContributorDto updateContributor(long id, ContributorEditProfileRequest contributorRequest) {
        Contributor contributor = contributorRepository.findContributorById(id);

        if (contributor == null) {
            log.error("Attempted to edit NULL Contributor");
            throw new IllegalArgumentException("Contributor cannot be null");
        }

        if (contributorRequest.username() != null) {
            contributor.setUsername(contributorRequest.username());
        }

        if (contributorRequest.name() != null) {
            contributor.setName(contributorRequest.name());
        }

        if (contributorRequest.password() != null) {
            contributor.setPassword(contributorRequest.password());
        }

        if (contributorRequest.phoneNumber() != null) {
            contributor.setPhone(contributorRequest.phoneNumber());
        }

        if (contributorRequest.address() != null) {
            contributor.setAddress(contributorRequest.address());
        }

        if (contributorRequest.typeOfUser() != null) {
            contributor.setTypeOfUser(contributorRequest.typeOfUser());
        }

        if (contributorRequest.description() != null) {
            contributor.setDescription(contributorRequest.description());
        }

        contributorRepository.save(contributor);
        log.info("Contributor updated successfully, id: {}", contributor.getId());
        return ContributorDto.fromEntity(contributor);
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

    public Contributor findContributorById(Long id) {
        return contributorRepository.findContributorById(id);
    }

    public Contributor findContributorByUsername(String username) {
        return contributorRepository.findContributorByUsername(username);
    }

    public Contributor findContributorByEmail(String email) {
        return contributorRepository.findContributorByEmail(email);
    }
}
