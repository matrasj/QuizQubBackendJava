package com.example.quizqubbackendjava.model.payload.question;

import com.example.quizqubbackendjava.model.payload.option.OptionPayloadRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class QuestionPayloadRequest {
    private String subjectName;
    private String content;
    private String correctOption;
    private Set<OptionPayloadRequest> options = new HashSet<>();


}
