package com.example.there4u.service.badge;

import com.example.there4u.model.badge.Badge;
import com.example.there4u.repository.badge.BadgeRepository;
import com.example.there4u.service.general_for_users.RegularUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class BadgeService {
    private final BadgeRepository badgeRepository;

    public BadgeService(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    public Optional<Badge> findBadgeById(long id) {
        return badgeRepository.findById(id);
    }
}
