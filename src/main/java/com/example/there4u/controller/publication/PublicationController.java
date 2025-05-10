package com.example.there4u.controller.publication;

import com.example.there4u.dto.publication.PublicationRequestDto;
import com.example.there4u.dto.publication.PublicationResponseDto;
import com.example.there4u.service.publication.PublicationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicationController {
    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @PostMapping("/api/users/{ownerId}/publications")
    public ResponseEntity<?> publish(
            @PathVariable Long ownerId,
            @RequestBody @Valid PublicationRequestDto request) {

        try {
            PublicationResponseDto response = publicationService.publish(request, ownerId);
            return ResponseEntity.ok(response);
        }
        catch(EntityNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
