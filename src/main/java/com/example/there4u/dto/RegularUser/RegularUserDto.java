package com.example.there4u.dto.RegularUser;

import com.example.there4u.model.user.RegularUser;

public record RegularUserDto(
        long id,
        String username,
        String name,
        String address,
        String phone,
        String email,
        String ucn
) {
    // Optional: static factory method for mapping fromEntity entity
    public static RegularUserDto fromEntity(RegularUser item) {
        return new RegularUserDto(
                item.getId(),
                item.getUsername(),
                item.getName(),
                item.getAddress(),
                item.getPhone(),
                item.getEmail(),
                item.getUCN()
        );
    }
}
