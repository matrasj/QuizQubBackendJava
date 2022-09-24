package com.example.quizqubbackendjava.repository;

import com.example.quizqubbackendjava.model.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
}
