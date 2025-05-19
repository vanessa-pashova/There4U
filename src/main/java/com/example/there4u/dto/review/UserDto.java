package com.example.there4u.dto.review;

import com.example.there4u.model.user.User;

public record UserDto(
        Long id,
        String name,
        String typeOfUser
) {
        public static UserDto fromEntity(User user) {
                return new UserDto(
                        user.getId(),
                        user.getName(),
                        user.getTypeOfUser().name()
                );
        }
}
