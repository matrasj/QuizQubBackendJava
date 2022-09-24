package com.example.quizqubbackendjava.service;

import com.example.quizqubbackendjava.model.payload.session.SessionNumberPayloadResponse;
import com.example.quizqubbackendjava.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;


}
