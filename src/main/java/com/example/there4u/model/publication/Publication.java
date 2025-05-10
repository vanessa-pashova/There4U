package com.example.there4u.model.publication;

import com.example.there4u.dto.publication.PublicationRequestDto;
import com.example.there4u.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "publication")
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotBlank
    @Column(name = "title")
    private String title;

    @NotBlank
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public Publication(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Publication(PublicationRequestDto publicationRequestDto) {
        this.title = publicationRequestDto.title();
        this.description = publicationRequestDto.description();
    }
}
