package com.example.quizqubbackendjava.repository;

import com.example.quizqubbackendjava.model.entity.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findTopByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);
    Page<Session> findByUserRoleName(@Param("roleName") String roleName, Pageable pageable);
    Page<Session> findByUserRoleNameAndSubjectName(@Param("roleName") String roleName, @Param("subjectName") String subjectName, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT CONCAT_WS(',',subject.name ,AVG(session.percentage_score))  FROM subject LEFT JOIN session \n" +
            "ON session.subject_id = subject.id \n" +
            "LEFT JOIN user ON session.user_id = user.id\n" +
            " AND  user.id = :userId GROUP BY subject.name ORDER BY AVG(session.percentage_score) DESC")
    List<String> findAveragesForEverySubjectByUserId(Long userId);
}

