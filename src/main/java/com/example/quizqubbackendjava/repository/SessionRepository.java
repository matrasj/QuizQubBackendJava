package com.example.quizqubbackendjava.repository;

import com.example.quizqubbackendjava.model.entity.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findTopByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);
    Page<Session> findByUserRoleName(@Param("roleName") String roleName, Pageable pageable);
}
