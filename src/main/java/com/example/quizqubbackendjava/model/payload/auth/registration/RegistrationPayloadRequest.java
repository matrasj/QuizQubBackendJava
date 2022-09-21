package com.example.quizqubbackendjava.model.payload.auth.registration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RegistrationPayloadRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
}
