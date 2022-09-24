package com.example.quizqubbackendjava.controller;

import com.example.quizqubbackendjava.model.payload.subject.SubjectPayloadResponse;
import com.example.quizqubbackendjava.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<SubjectPayloadResponse>> getSubjects() {
        return ResponseEntity.status(OK)
                .body(subjectService.findAllSubjects());
    }

}
