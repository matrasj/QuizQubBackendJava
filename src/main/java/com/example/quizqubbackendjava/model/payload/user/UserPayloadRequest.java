package com.example.quizqubbackendjava.model.payload.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserPayloadRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String roleName;
}
