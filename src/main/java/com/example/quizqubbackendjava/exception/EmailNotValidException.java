package com.example.quizqubbackendjava.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class EmailNotValidException extends RuntimeException{
    public EmailNotValidException(String message) {
        super(message);
    }
}
