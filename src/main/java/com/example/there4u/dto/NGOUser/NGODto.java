package com.example.there4u.dto.NGOUser;

import com.example.there4u.model.user.NGOUser;

public record NGODto(
        long id,
        String username,
        String name,
        String address,
        String phone,
        String email,
        String description
) {
    // Optional: static factory method for mapping fromEntity entity
    public static NGODto fromEntity(NGOUser item) {
        return new NGODto(
                item.getId(),
                item.getUsername(),
                item.getName(),
                item.getAddress(),
                item.getPhone(),
                item.getEmail(),
                item.getDescription()
        );
    }
}
