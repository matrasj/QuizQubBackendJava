package com.example.quizqubbackendjava.mapper;

import com.example.quizqubbackendjava.model.entity.Session;
import com.example.quizqubbackendjava.model.payload.session.SessionPayloadResponse;

public class SessionPayloadResponseMapper {
    public static SessionPayloadResponse mapToSessionPayloadResponse(Session session) {
        return SessionPayloadResponse.builder()
                .id(session.getId())
                .createdAt(session.getCreatedAt())
                .userId(session.getUser().getId())
                .subjectName(session.getSubject().getName())
                .userFirstName(session.getUser().getFirstName())
                .userLastName(session.getUser().getLastName())
                .userUsername(session.getUser().getUsername())
                .minutesMaxDuration(session.getMinutesMaxDuration())
                .durationTime(session.getDurationTime())
                .finished(session.isFinished())
                .percentageScore(session.getPercentageScore())
                .build();
    }
}
