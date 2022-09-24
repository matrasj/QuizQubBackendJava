package com.example.quizqubbackendjava.service;

import com.example.quizqubbackendjava.exception.QuestionNotFoundException;
import com.example.quizqubbackendjava.exception.SubjectNotFoundException;
import com.example.quizqubbackendjava.mapper.QuestionPayloadMapper;
import com.example.quizqubbackendjava.mapper.QuestionPayloadResponseMapper;
import com.example.quizqubbackendjava.model.entity.Option;
import com.example.quizqubbackendjava.model.entity.Question;
import com.example.quizqubbackendjava.model.payload.option.OptionPayloadRequest;
import com.example.quizqubbackendjava.model.payload.question.QuestionPayload;
import com.example.quizqubbackendjava.model.payload.question.QuestionPayloadRequest;
import com.example.quizqubbackendjava.model.payload.question.QuestionPayloadResponse;
import com.example.quizqubbackendjava.repository.OptionRepository;
import com.example.quizqubbackendjava.repository.QuestionRepository;
import com.example.quizqubbackendjava.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private static final String SUBJECT_NOT_FOUND_MESSAGE = "Not found subject %s";
    private static final String SUCCESSFULLY_ADD_OR_CHANGED_QUESTION = "Success";
    private static final String QUESTION_NOT_FOUND_MESSAGE = "Not found question with id %d";
    private static final String SUCCESSFULLY_DELETION_MESSAGE = "Successfully deleted question";
    private final QuestionRepository questionRepository;
    private final SubjectRepository subjectRepository;
    private final OptionRepository optionRepository;

    @Transactional
    public String createQuestion(QuestionPayloadRequest questionPayloadRequest) {
        Question question = buildQuestionFromPayload(questionPayloadRequest);

        question.getOptions().forEach((option) -> {
            question.getOptions().add(option);
            option.setQuestion(question);
        });

        questionRepository.save(question);

        return SUCCESSFULLY_ADD_OR_CHANGED_QUESTION;
    }

    @Transactional
    public String updateQuestion(QuestionPayloadRequest questionPayloadRequest, Long questionId) {
        Question existingQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(String.format(QUESTION_NOT_FOUND_MESSAGE, questionId)));

        Question question = buildQuestionFromPayload(questionPayloadRequest);

        existingQuestion.setContent(question.getContent());
        existingQuestion.setCorrectOption(question.getCorrectOption());

        optionRepository.deleteAll(existingQuestion.getOptions());
        existingQuestion.setOptions(question.getOptions());



        existingQuestion.getOptions().forEach((option) -> {
            existingQuestion.getOptions().add(option);
            option.setQuestion(existingQuestion);
        });

        questionRepository.save(existingQuestion);

        return SUCCESSFULLY_ADD_OR_CHANGED_QUESTION;
    }

    @Transactional
    protected Question buildQuestionFromPayload(QuestionPayloadRequest questionPayloadRequest) {
        Set<Option> options = questionPayloadRequest.getOptions()
                .stream().map((this::buildOptionFormPayload))
                .collect(Collectors.toSet());

        return Question.builder()
                .content(questionPayloadRequest.getContent())
                .subject(subjectRepository.findByName(questionPayloadRequest.getSubjectName())
                        .orElseThrow(() -> new SubjectNotFoundException(String.format(SUBJECT_NOT_FOUND_MESSAGE, questionPayloadRequest.getSubjectName()))))
                .options(options)
                .correctOption(
                        options
                                .stream()
                                .filter((option -> option.getContent().equals(questionPayloadRequest.getCorrectOption())))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("Invalid data"))
                ).build();
    }

    private Option buildOptionFormPayload(OptionPayloadRequest optionPayloadRequest) {
        return Option.builder()
                .content(optionPayloadRequest.getContent())
                .build();
    }

    public Page<QuestionPayloadResponse> findQuestionsWithPagination(int pageNumber, int pageSize) {
        Page<Question> questionsWithPagination
                = questionRepository.findAll(PageRequest.of(pageNumber, pageSize));

        return new PageImpl<>(
                questionsWithPagination
                        .stream()
                        .map(QuestionPayloadResponseMapper::mapToQuestionPayloadResponse)
                        .collect(Collectors.toList()),
                PageRequest.of(pageNumber, pageSize),
                questionsWithPagination.getTotalElements()
        );
    }

    public QuestionPayload findQuestionById(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(String.format(QUESTION_NOT_FOUND_MESSAGE, questionId)));

        return QuestionPayloadMapper.mapToQuestionPayload(question);
    }


    public String deleteQuestion(Long questionId) {
        questionRepository.deleteById(questionId);

        return SUCCESSFULLY_DELETION_MESSAGE;
    }

    public Page<QuestionPayloadResponse> findQuestionsWithPaginationByContentContaining(int pageNumber, int pageSize, String keyword) {
        Page<Question> questionsByKeyword =
                questionRepository.findByContentContaining(keyword, PageRequest.of(pageNumber, pageSize));

        return new PageImpl<>(
                questionsByKeyword
                        .stream()
                        .map(QuestionPayloadResponseMapper::mapToQuestionPayloadResponse)
                        .collect(Collectors.toList()),
                PageRequest.of(pageNumber, pageSize),
                questionsByKeyword.getTotalElements()
        );
    }

    public List<QuestionPayload> findQuestionsBySubjectName(String subjectName) {
        List<Question> questionsBySubjectName
                = questionRepository.findBySubjectName(subjectName);

        return questionsBySubjectName
                .stream()
                .map(QuestionPayloadMapper::mapToQuestionPayload)
                .collect(Collectors.toList());
    }
}
