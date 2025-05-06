package com.example.there4u.dto.Contributor;

import com.example.there4u.model.user.Contributor;

public record ContributorDto(
        long id,
        String username,
        String name,
        String address,
        String phone,
        String email,
        String typeOfUser,
        String description
) {
    // Optional: static factory method for mapping fromEntity entity
    public static ContributorDto fromEntity(Contributor item) {
        return new ContributorDto(
                item.getId(),
                item.getUsername(),
                item.getName(),
                item.getAddress(),
                item.getPhone(),
                item.getEmail(),
                item.getTypeOfUser().toString(),
                item.getDescription()
        );
    }
}
