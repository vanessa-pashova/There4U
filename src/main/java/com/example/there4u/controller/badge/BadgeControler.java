package com.example.there4u.controller.badge;

import com.example.there4u.model.badge.Badge;
import com.example.there4u.service.badge.BadgeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/badges")
public class BadgeControler {
    private final BadgeService badgeService;

    public BadgeControler(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Badge> getBadge(@PathVariable Long id) {
        Badge badge = badgeService.findBadgeById(id).orElse(null);
        if(badge == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(badge, HttpStatus.OK);
    }
}
