package com.example.quizqubbackendjava.repository;

import com.example.quizqubbackendjava.model.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByContent(@Param("content") String content);
    void deleteByContent(@Param("content") String content);
    Page<Question> findByContentContaining(@Param("keyword") String keyword, Pageable pageable);
    Page<Question> findBySubjectName(@Param("subjectName") String subjectName, Pageable pageable);
    List<Question> findBySubjectName(@Param("subjectName") String subjectName);


}
