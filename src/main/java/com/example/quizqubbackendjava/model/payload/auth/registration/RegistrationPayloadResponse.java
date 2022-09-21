package com.example.quizqubbackendjava.model.payload.auth.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationPayloadResponse {
    private String token;
}
