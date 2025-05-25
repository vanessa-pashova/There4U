package com.example.there4u.dto.user;

import com.example.there4u.model.user.User;

public record UserDto (
        long id,
        String username,
        String name,
        String address,
        String phone,
        String email
){
    public UserDto(User user) {
        this(user.getId(),
                user.getUsername(),
                user.getName(),
                user.getAddress(),
                user.getPhone(),
                user.getEmail()
        );
    }
}
