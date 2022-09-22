package com.example.quizqubbackendjava.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String forAdmin() {
        return "Has access for admin";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String forUser() {
        return "Has access for user";
    }

    @GetMapping("/teacher")
    @PreAuthorize("hasRole('TEACHER')")
    public String forTeacher() {
        return "Has access for teacher";
    }
}
