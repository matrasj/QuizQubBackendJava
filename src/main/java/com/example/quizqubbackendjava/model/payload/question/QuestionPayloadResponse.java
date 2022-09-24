package com.example.quizqubbackendjava.model.payload.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class QuestionPayloadResponse {
    private Long id;
    private String content;
    private String subjectName;
}
