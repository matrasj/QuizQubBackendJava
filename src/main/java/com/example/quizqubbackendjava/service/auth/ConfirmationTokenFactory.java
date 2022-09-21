package com.example.quizqubbackendjava.service.auth;

import com.example.quizqubbackendjava.model.entity.ConfirmationToken;

import java.time.LocalDateTime;
import java.util.UUID;

public class ConfirmationTokenFactory {
    public static ConfirmationToken createConfirmationToken() {
        return ConfirmationToken.builder()
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .token(generateToken())
                .build();
    }

    private static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
