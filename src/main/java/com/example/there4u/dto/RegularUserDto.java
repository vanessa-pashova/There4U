package com.example.there4u.dto;

import com.example.there4u.model.user.RegularUser;
import jakarta.validation.constraints.Null;

public record RegularUserDto(
        long id,
        String username,
        String name,
        String address,
        String phone,
        String email,
        String password,
        String ucn
) {
    // Optional: static factory method for mapping from entity
    public static RegularUserDto fromEntity(RegularUser item) {
        return new RegularUserDto(
                item.getId(),
                item.getUsername(),
                item.getName(),
                item.getAddress(),
                item.getPhone(),
                item.getEmail(),
                item.getPassword(),
                item.getUCN()
        );
    }
}
