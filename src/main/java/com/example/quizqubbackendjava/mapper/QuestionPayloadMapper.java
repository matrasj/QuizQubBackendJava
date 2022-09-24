package com.example.quizqubbackendjava.mapper;

import com.example.quizqubbackendjava.model.entity.Question;
import com.example.quizqubbackendjava.model.payload.option.OptionPayloadResponse;
import com.example.quizqubbackendjava.model.payload.question.QuestionPayload;

import java.util.stream.Collectors;

public class QuestionPayloadMapper {
    public static QuestionPayload mapToQuestionPayload(Question question) {
        return QuestionPayload.builder()
                .id(question.getId())
                .content(question.getContent())
                .subjectName(question.getSubject().getName())
                .correctAnswer(question.getCorrectOption().getContent())
                .options(
                        question.getOptions()
                                .stream()
                                .map(OptionPayloadResponseMapper::mapToOptionPayloadResponse)
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
