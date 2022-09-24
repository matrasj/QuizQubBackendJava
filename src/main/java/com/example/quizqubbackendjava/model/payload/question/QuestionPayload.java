package com.example.quizqubbackendjava.model.payload.question;

import com.example.quizqubbackendjava.model.payload.option.OptionPayloadResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class QuestionPayload {
    private Long id;
    private String content;
    private String correctAnswer;
    private String subjectName;
    private Set<OptionPayloadResponse> options = new HashSet<>();
}
