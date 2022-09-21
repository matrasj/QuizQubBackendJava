package com.example.quizqubbackendjava.repository;


import com.example.quizqubbackendjava.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(@Param("name") String name);
}
