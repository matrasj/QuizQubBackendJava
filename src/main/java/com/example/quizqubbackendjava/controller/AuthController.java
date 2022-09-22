package com.example.quizqubbackendjava.controller;

import com.example.quizqubbackendjava.model.payload.auth.login.LoginPayloadRequest;
import com.example.quizqubbackendjava.model.payload.auth.login.LoginPayloadResponse;
import com.example.quizqubbackendjava.model.payload.auth.registration.RegistrationPayloadRequest;
import com.example.quizqubbackendjava.model.payload.auth.registration.RegistrationPayloadResponse;
import com.example.quizqubbackendjava.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

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

    @GetMapping("/confirmation")
    public ResponseEntity<String> confirmToken(@RequestParam String token) {
        return ResponseEntity.status(ACCEPTED)
                .body(authService.confirmToken(token));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginPayloadResponse> login(@RequestBody LoginPayloadRequest loginPayloadRequest) {
        return ResponseEntity.status(OK)
                .body(authService.loginUser(loginPayloadRequest));
    }


}
