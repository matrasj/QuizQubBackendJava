package com.example.quizqubbackendjava.model.payload.session;

import com.example.quizqubbackendjava.model.entity.Subject;
import com.example.quizqubbackendjava.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SessionPayloadResponse {
    private Long id;
    private Date createdAt;
    private Long userId;
    private String subjectName;
    private String userFirstName;
    private String userLastName;
    private String userUsername;
    private int minutesMaxDuration;
    private String durationTime;
    private boolean finished;
    private BigDecimal percentageScore;
}
