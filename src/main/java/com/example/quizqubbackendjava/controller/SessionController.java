package com.example.quizqubbackendjava.controller;

import com.example.quizqubbackendjava.model.entity.Session;
import com.example.quizqubbackendjava.model.payload.session.SessionNumberPayloadResponse;
import com.example.quizqubbackendjava.model.payload.session.SessionPayloadRequest;
import com.example.quizqubbackendjava.model.payload.session.SessionPayloadResponse;
import com.example.quizqubbackendjava.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/sessions")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> createStudentSession(@RequestBody SessionPayloadRequest sessionPayloadRequest) {
        return ResponseEntity.status(CREATED)
                .body(sessionService.saveStudentSession(sessionPayloadRequest));
    }

    @GetMapping("/latest/student/{userId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<SessionPayloadResponse> getLatestSessionByStudentId(@PathVariable Long userId) {
        return ResponseEntity.status(OK)
                .body(sessionService.findLatestSessionByUserId(userId));
    }

    @GetMapping("/pagination/students")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Page<SessionPayloadResponse>> getStudentSessionsForTeacherDashboard(@RequestParam int pageNumber,
                                                                                              @RequestParam int pageSize) {
        return ResponseEntity.status(OK)
                .body(sessionService.findStudentSessionsForTeacherDashboard(pageNumber, pageSize));
    }

}
