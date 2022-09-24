package com.example.quizqubbackendjava.repository;

import com.example.quizqubbackendjava.model.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

}
