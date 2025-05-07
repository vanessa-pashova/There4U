package com.example.there4u.model.badge;

import com.example.there4u.model.image.Image;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "badge")
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long badgeId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "requirements")
    private String requirements;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "icon_id", referencedColumnName = "id")
    private Image icon;
}
