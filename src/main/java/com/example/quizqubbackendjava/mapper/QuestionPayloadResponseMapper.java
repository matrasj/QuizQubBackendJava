package com.example.quizqubbackendjava.mapper;

import com.example.quizqubbackendjava.model.entity.Question;
import com.example.quizqubbackendjava.model.payload.question.QuestionPayloadResponse;

public class QuestionPayloadResponseMapper {
    public static QuestionPayloadResponse mapToQuestionPayloadResponse(Question question) {
        return QuestionPayloadResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .subjectName(question.getSubject().getName())
                .build();
    }
}
