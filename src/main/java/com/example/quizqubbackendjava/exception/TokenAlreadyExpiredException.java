package com.example.quizqubbackendjava.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class TokenAlreadyExpiredException extends RuntimeException{
    public TokenAlreadyExpiredException(String message) {
        super(message);
    }
}
