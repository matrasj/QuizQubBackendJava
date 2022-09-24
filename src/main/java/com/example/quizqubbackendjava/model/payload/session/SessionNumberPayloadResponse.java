package com.example.quizqubbackendjava.model.payload.session;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionNumberPayloadResponse {
    private int attempts;
}
