package com.example.quizqubbackendjava.mapper;

import com.example.quizqubbackendjava.model.entity.User;
import com.example.quizqubbackendjava.model.payload.user.UserPayloadResponse;

public class UserPayloadResponseMapper {
    public static UserPayloadResponse mapToUserPayloadResponse(User user) {
        return UserPayloadResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .lastUpdated(user.getLastUpdated())
                .roleName(user.getRole().getName())
                .build();
    }
}
