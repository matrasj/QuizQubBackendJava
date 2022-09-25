package com.example.quizqubbackendjava.model.payload.session;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SessionPayloadRequest {
    private Long userId;
    private String subjectName;
    private int minutesMaxDuration;
    private String durationTime;
    private boolean finished;
    private String percentageScore;
}
