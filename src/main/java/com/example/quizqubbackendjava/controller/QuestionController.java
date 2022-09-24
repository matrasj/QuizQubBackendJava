package com.example.quizqubbackendjava.controller;

import com.example.quizqubbackendjava.model.payload.question.QuestionPayload;
import com.example.quizqubbackendjava.model.payload.question.QuestionPayloadRequest;
import com.example.quizqubbackendjava.model.payload.question.QuestionPayloadResponse;
import com.example.quizqubbackendjava.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> createQuestion(@RequestBody QuestionPayloadRequest questionPayloadRequest) {
        return ResponseEntity.status(CREATED)
                .body(questionService.createQuestion(questionPayloadRequest));
    }

    @PostMapping("/update/{questionId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> updateQuestion(@RequestBody QuestionPayloadRequest questionPayloadRequest,
                                                 @PathVariable Long questionId) {
        return ResponseEntity.status(ACCEPTED)
                .body(questionService.updateQuestion(questionPayloadRequest, questionId));
    }

    @DeleteMapping("/delete/{questionId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long questionId) {
        return ResponseEntity.status(OK)
                .body(questionService.deleteQuestion(questionId));
    }

    @GetMapping("/pagination")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Page<QuestionPayloadResponse>> getQuestionsWithPagination(@RequestParam int pageNumber,
                                                                                    @RequestParam int pageSize) {
        return ResponseEntity.status(OK)
                .body(questionService.findQuestionsWithPagination(pageNumber, pageSize));
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionPayload> getQuestionById(@PathVariable Long questionId) {
        return ResponseEntity.status(OK)
                .body(questionService.findQuestionById(questionId));
    }



}
