package com.example.there4u.dto.badge;

import com.example.there4u.dto.image.ImageDto;
import com.example.there4u.model.badge.Badge;

public record BadgeDto(Long id, String title, String description, String requirements, ImageDto image) {
    public BadgeDto(Badge badge) {
        this(
            badge.getBadgeId(),
            badge.getTitle(),
            badge.getDescription(),
            badge.getRequirements(),
            new ImageDto(badge.getIcon())
        );
    }
}
