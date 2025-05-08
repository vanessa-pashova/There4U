package com.example.there4u.service.badge;

import com.example.there4u.model.badge.Badge;
import com.example.there4u.model.user.Contributor;
import com.example.there4u.model.user.NGOUser;
import com.example.there4u.model.user.RegularUser;
import com.example.there4u.model.user.User;
import com.example.there4u.repository.badge.BadgeRepository;
import com.example.there4u.repository.general_users.ContributorRepository;
import com.example.there4u.repository.general_users.NGORepository;
import com.example.there4u.repository.general_users.RegularUserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@Slf4j
@Service
public class BadgeAssignmentService {
    private final ContributorRepository contributorRepository;
    private final NGORepository ngoRepository;
    private final RegularUserRepository regularUserRepository;
    private final BadgeRepository badgeRepository;

    public BadgeAssignmentService(ContributorRepository contributorRepository, NGORepository ngoRepository, RegularUserRepository regularUserRepository, BadgeRepository badgeRepository) {
        this.contributorRepository = contributorRepository;
        this.ngoRepository = ngoRepository;
        this.regularUserRepository = regularUserRepository;
        this.badgeRepository = badgeRepository;
    }

    public Set<Badge> getBadgesByUserId(Long userId) {
        User user = findUserById(userId);
        if(user == null) {
            throw new EntityNotFoundException("User {" + userId +"} not found");
        }
        return user.getBadges();
    }

    @Transactional
    public void assignBadgeToUser(Long userId, Long badgeId) {
        Badge badge = badgeRepository.findById(badgeId).orElse(null);
        if(badge == null) {
            log.error("Badge {" + badgeId + "} not found. Assigning badge to user went wrong.");
            throw new EntityNotFoundException("Badge {" + badgeId + "} not found.");
        }

        User user = findUserById(userId);

        if(user == null) {
            log.error("User {" + userId + "} not found. Assigning badge to user went wrong.");
            throw new EntityNotFoundException("User {" + userId + "} not found.");
        }

        user.addBadge(badge);

        if (user instanceof Contributor contributor) contributorRepository.save(contributor);
        else if (user instanceof NGOUser ngoUser) ngoRepository.save(ngoUser);
        else if (user instanceof RegularUser regularUser) regularUserRepository.save(regularUser);

        log.info("Badge {"+ badgeId + "} assigned successfully to user " + userId);
    }

    private User findUserById(Long userId) {
        User user = contributorRepository.findContributorById(userId);
        if(user == null) {
            user = regularUserRepository.findById(userId).orElse(null);
        }
        if(user == null) {
            user = ngoRepository.findById(userId).orElse(null);
        }
        return user;
    }
}
