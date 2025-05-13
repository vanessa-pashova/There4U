package com.example.there4u.service.publication;

import com.example.there4u.dto.publication.PublicationEditRequestDto;
import com.example.there4u.dto.publication.PublicationRequestDto;
import com.example.there4u.dto.publication.PublicationResponseDto;
import com.example.there4u.model.publication.Publication;
import com.example.there4u.model.user.User;
import com.example.there4u.repository.general_users.UserRepository;
import com.example.there4u.repository.publication.PublicationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PublicationService {
    private final PublicationRepository publicationRepository;
    private final UserRepository userRepository;

    public PublicationService(PublicationRepository publicationRepository, UserRepository userRepository) {
        this.publicationRepository = publicationRepository;
        this.userRepository = userRepository;
    }

    public PublicationResponseDto publish(PublicationRequestDto publicationRequest, Long ownerId) {
        User owner = userRepository.findById(ownerId).
                orElseThrow(() -> new EntityNotFoundException("Could not find user with id " + ownerId));

        Publication publication = new Publication(publicationRequest);
        publication.setOwner(owner);

        Publication savedPublication = publicationRepository.save(publication);

        return new PublicationResponseDto(savedPublication);
    }

    public List<Publication> findByOwnerId(Long ownerId) {
        return publicationRepository.findByOwnerId(ownerId);
    }

    public PublicationResponseDto updatePublication(Long id, Long ownerId, PublicationEditRequestDto publicationEditRequest) {
        Publication publication = publicationRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Could not find publication with id " + id));
        if(publication.getOwner().getId() != ownerId) {
            String errorMessage = "Tried to update a publication that doesn't belong to user with id: "+ ownerId + ". The publication with id = " + id + " belongs to user with id = " + publication.getOwner().getId();
            throw new IllegalArgumentException(errorMessage);
        }

        publication.update(publicationEditRequest);
        publicationRepository.save(publication);
        log.info("Publication updated successfully, id: {}", id);
        return new PublicationResponseDto(publication);
    }

    public void deletePublication(Long id, Long ownerId) {
        Publication publication = publicationRepository.findById(id).orElse(null);
        if(publication != null && publication.getOwner().getId() != ownerId) {
            String errorMessage = "Tried to delete a publication that doesn't belong to user with id: "+ ownerId + ". The publication with id = " + id + " belongs to user with id = " + publication.getOwner().getId();
            throw new IllegalArgumentException(errorMessage);
        }

        publicationRepository.findById(id).ifPresent(publicationRepository::delete);
    }
}
