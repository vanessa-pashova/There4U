package com.example.there4u.dto.publication;

import com.example.there4u.model.publication.Publication;
import jakarta.validation.constraints.NotBlank;

public record PublicationRequestDto(
        @NotBlank
        String title,
        @NotBlank
        String description) {
    public PublicationRequestDto(Publication publication) {
        this(
                publication.getTitle(),
                publication.getDescription());
    }
}
