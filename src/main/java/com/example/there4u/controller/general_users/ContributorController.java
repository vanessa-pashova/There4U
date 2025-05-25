package com.example.there4u.controller.general_users;

import com.example.there4u.dto.Contributor.ContributorDto;
import com.example.there4u.dto.Contributor.ContributorEditProfileRequest;
import com.example.there4u.dto.Contributor.ContributorRegisterRequest;
import com.example.there4u.model.user.Contributor;
import com.example.there4u.service.general_for_users.ContributorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contributor-users")
public class ContributorController {
    private final ContributorService contributorService;

    public ContributorController(ContributorService contributorService) {
        this.contributorService = contributorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContributorDto> getContributor(@PathVariable Long id) {
        Contributor contributor = contributorService.findContributorById(id);

        if (contributor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ContributorDto(contributor), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ContributorDto> createItem(@Valid @RequestBody ContributorRegisterRequest request) {
        ContributorDto created = contributorService.createContributor(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContributorDto> updateItem(@PathVariable Long id, @Valid @RequestBody ContributorEditProfileRequest request) {
        Contributor contributor = contributorService.findContributorById(id);

        if (contributor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ContributorDto updated = contributorService.updateContributor(id, request);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        Contributor contributor = contributorService.findContributorById(id);

        if (contributor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        contributorService.deleteContributor(contributor);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
