package com.example.quizqubbackendjava.service;

import com.example.quizqubbackendjava.model.entity.Subject;
import com.example.quizqubbackendjava.model.payload.subject.SubjectPayloadResponse;
import com.example.quizqubbackendjava.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository questionCategoryRepository;

    public List<SubjectPayloadResponse> findAllSubjects() {
        return questionCategoryRepository
                .findAll()
                .stream()
                .map(questionCategory -> new SubjectPayloadResponse(
                        questionCategory.getId(),
                        questionCategory.getName()
                ))
                .collect(Collectors.toList());
    }

    @PostConstruct
    @Transactional
    public void createSubjects() {
        Subject it = Subject
                .builder()
                .name("IT")
                .build();

        Subject maths = Subject
                .builder()
                .name("Maths")
                .build();

        Subject english = Subject
                .builder()
                .name("English")
                .build();

        Subject geography = Subject
                .builder()
                .name("Geography")
                .build();

        Subject biology = Subject
                .builder()
                .name("Biology")
                .build();

        questionCategoryRepository.saveAll(List.of(it, maths, english, geography, biology));
    }
}
