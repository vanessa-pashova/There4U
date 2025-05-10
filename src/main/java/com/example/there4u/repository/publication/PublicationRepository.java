package com.example.there4u.repository.publication;

import com.example.there4u.model.publication.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication> findByOwnerId(Long ownerId);
}
