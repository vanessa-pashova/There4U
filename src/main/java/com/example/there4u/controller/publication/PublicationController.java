package com.example.there4u.controller.publication;

import com.example.there4u.dto.publication.PublicationEditRequestDto;
import com.example.there4u.dto.publication.PublicationRequestDto;
import com.example.there4u.dto.publication.PublicationResponseDto;
import com.example.there4u.model.publication.Publication;
import com.example.there4u.service.publication.PublicationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/api/users/{ownerId}/publications")
    public ResponseEntity<?> getPublications(@PathVariable Long ownerId) {
        List<Publication> publications = publicationService.findByOwnerId(ownerId);

        List<PublicationResponseDto> responses = publications.stream()
                .map(PublicationResponseDto::new)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PutMapping("/api/users/{ownerId}/publications/{id}")
    public ResponseEntity<?> updatePublication(@PathVariable Long ownerId, @PathVariable Long id, @RequestBody PublicationEditRequestDto request) {
        try{
            PublicationResponseDto response = publicationService.updatePublication(id, ownerId, request);
            return ResponseEntity.ok(response);
        }
        catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/api/users/{ownerId}/publications/{id}")
    public ResponseEntity<?> deletePublication(@PathVariable Long ownerId, @PathVariable Long id) {
        try{
            publicationService.deletePublication(id, ownerId);
            return ResponseEntity.ok("Publication with id = " + id + " deleted sucessfully.");
        }
        catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
