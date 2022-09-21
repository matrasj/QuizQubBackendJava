package com.example.quizqubbackendjava.controller;

import com.example.quizqubbackendjava.model.payload.auth.registration.RegistrationPayloadRequest;
import com.example.quizqubbackendjava.model.payload.auth.registration.RegistrationPayloadResponse;
import com.example.quizqubbackendjava.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/registration")
    public ResponseEntity<RegistrationPayloadResponse> registerUser(@RequestBody RegistrationPayloadRequest
                                                                                registrationPayloadRequest) {
        return ResponseEntity.status(CREATED)
                .body(authService.registerUser(registrationPayloadRequest));
    }
}
