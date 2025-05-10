package com.example.there4u.dto.publication;

import com.example.there4u.model.publication.Publication;

public record PublicationResponseDto(Long id, String title, String description, Long ownerId) {
    public PublicationResponseDto(Publication publication) {
        this(
                publication.getId(),
                publication.getTitle(),
                publication.getDescription(),
                publication.getOwner().getId()
        );
    }
}
