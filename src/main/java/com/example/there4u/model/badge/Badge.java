package com.example.there4u.model.badge;

import jakarta.persistence.*;
import lombok.Getter;

import javax.swing.*;

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

    @Column(name = "icon")
    private ImageIcon icon;

    @Column(name = "requirements")
    private String requirements;
}
