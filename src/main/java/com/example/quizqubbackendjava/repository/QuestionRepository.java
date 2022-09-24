package com.example.quizqubbackendjava.repository;

import com.example.quizqubbackendjava.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByContent(@Param("content") String content);
    void deleteByContent(@Param("content") String content);

}
