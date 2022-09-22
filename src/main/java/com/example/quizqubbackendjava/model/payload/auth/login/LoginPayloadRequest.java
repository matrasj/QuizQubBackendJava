package com.example.quizqubbackendjava.model.payload.auth.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoginPayloadRequest {
    private String username;
    private String password;
}
