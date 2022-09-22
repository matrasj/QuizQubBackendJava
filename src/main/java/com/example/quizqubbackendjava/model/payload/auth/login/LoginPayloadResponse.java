package com.example.quizqubbackendjava.model.payload.auth.login;

import com.example.quizqubbackendjava.model.payload.user.UserPayloadResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoginPayloadResponse {
    private String jwtToken;
    private UserPayloadResponse userPayloadResponse;
}
