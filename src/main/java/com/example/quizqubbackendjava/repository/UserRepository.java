package com.example.quizqubbackendjava.repository;


import com.example.quizqubbackendjava.model.entity.User;
import com.example.quizqubbackendjava.model.payload.user.UserPayloadResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(@Param("username") String username);
    Page<User> findByRoleName(@Param("roleName") String roleName, Pageable pageable);

}
