package com.example.quizqubbackendjava.repository;

import com.example.quizqubbackendjava.model.entity.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;


@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findTopByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);
    Page<Session> findByUserRoleName(@Param("roleName") String roleName, Pageable pageable);
    Page<Session> findByUserRoleNameAndSubjectName(@Param("roleName") String roleName, String subjectName, Pageable pageable);
}
