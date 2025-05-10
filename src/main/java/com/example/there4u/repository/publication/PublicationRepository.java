package com.example.there4u.repository.publication;

import com.example.there4u.model.publication.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
