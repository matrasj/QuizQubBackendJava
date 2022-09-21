package com.example.quizqubbackendjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class QuizQubBackendJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizQubBackendJavaApplication.class, args);
    }

}
