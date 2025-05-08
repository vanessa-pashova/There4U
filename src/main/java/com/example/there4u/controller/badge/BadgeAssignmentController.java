package com.example.there4u.controller.badge;

import com.example.there4u.dto.badge.BadgeDto;
import com.example.there4u.model.badge.Badge;
import com.example.there4u.service.badge.BadgeAssignmentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class BadgeAssignmentController {

    private final BadgeAssignmentService badgeAssignmentService;

    public BadgeAssignmentController(BadgeAssignmentService badgeAssignmentService) {
        this.badgeAssignmentService = badgeAssignmentService;
    }

    @Transactional
    @GetMapping("{userId}/badges")
    public ResponseEntity<?> getBadges(@PathVariable Long userId) {
        try {
            Set<Badge> badges = badgeAssignmentService.getBadgesByUserId(userId);
            Set<BadgeDto> badgeDtos = badges.stream()
                    .map(BadgeDto::new)
                    .collect(Collectors.toSet());
            return ResponseEntity.ok(badgeDtos);
        }
        catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/{userId}/badges/{badgeId}")
    public ResponseEntity<String> assignBadgeToUser(
            @PathVariable Long userId,
            @PathVariable Long badgeId
    ) {
        try {
            badgeAssignmentService.assignBadgeToUser(userId, badgeId);
            return ResponseEntity.ok("Badge {" + badgeId + "} assigned succesfully to user {" + userId + "}");
        }
        catch(EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
