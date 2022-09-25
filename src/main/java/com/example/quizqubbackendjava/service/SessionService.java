package com.example.quizqubbackendjava.service;

import com.example.quizqubbackendjava.exception.SubjectNotFoundException;
import com.example.quizqubbackendjava.exception.UserNotFoundException;
import com.example.quizqubbackendjava.mapper.SessionPayloadResponseMapper;
import com.example.quizqubbackendjava.model.entity.Session;
import com.example.quizqubbackendjava.model.entity.Subject;
import com.example.quizqubbackendjava.model.entity.User;
import com.example.quizqubbackendjava.model.payload.session.SessionNumberPayloadResponse;
import com.example.quizqubbackendjava.model.payload.session.SessionPayloadRequest;
import com.example.quizqubbackendjava.model.payload.session.SessionPayloadResponse;
import com.example.quizqubbackendjava.repository.SessionRepository;
import com.example.quizqubbackendjava.repository.SubjectRepository;
import com.example.quizqubbackendjava.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionService {
    private static final String USER_NOT_FOUND_MESSAGE = "Not found user with id --> %d";
    private static final String SUBJECT_NOT_FOUND_MESSAGE = "Not found subject with name --> %s";
    private static final String SUCCESSFULLY_SESSION_CREATION = "Successfully created session!";
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    @Transactional
    public String saveStudentSession(SessionPayloadRequest sessionPayloadRequest) {
        buildSessionFromPayloadAndSave(sessionPayloadRequest);
        
        return SUCCESSFULLY_SESSION_CREATION;
    }
    
    @Transactional
    protected void buildSessionFromPayloadAndSave(SessionPayloadRequest sessionPayloadRequest) {
        User user = userRepository.findById(sessionPayloadRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, sessionPayloadRequest.getUserId())));

        Subject existingSubject = subjectRepository.findByName(sessionPayloadRequest.getSubjectName())
                .orElseThrow(() -> new SubjectNotFoundException(String.format(SUBJECT_NOT_FOUND_MESSAGE, sessionPayloadRequest.getSubjectName())));
        
        Session buildSession = Session.builder()
                .user(user)
                .subject(existingSubject)
                .minutesMaxDuration(sessionPayloadRequest.getMinutesMaxDuration())
                .finished(sessionPayloadRequest.isFinished())
                .durationTime(sessionPayloadRequest.getDurationTime())
                .percentageScore(sessionPayloadRequest.getPercentageScore())
                .build();
        
        user.getSessions().add(buildSession);
        existingSubject.getSessions().add(buildSession);
        
        sessionRepository.save(buildSession);
        userRepository.save(user);
        subjectRepository.save(existingSubject);
    }

    public SessionPayloadResponse findLatestSessionByUserId(Long userId) {
        Session latestSessionForUser = sessionRepository.findTopByUserIdOrderByCreatedAtDesc(userId);
        if (latestSessionForUser == null) {
            return null;
        }

        return SessionPayloadResponseMapper.mapToSessionPayloadResponse(latestSessionForUser);
    }

    public Page<SessionPayloadResponse> findStudentSessionsForTeacherDashboard(int pageNumber, int pageSize) {
        Page<Session> studentSessions
                = sessionRepository.findByUserRoleName("STUDENT", PageRequest.of(pageNumber, pageSize));

        return new PageImpl<>(
                studentSessions
                        .stream()
                        .map(SessionPayloadResponseMapper::mapToSessionPayloadResponse)
                        .collect(Collectors.toList()),
                PageRequest.of(pageNumber, pageSize),
                studentSessions.getTotalElements()
        );
    }
}
