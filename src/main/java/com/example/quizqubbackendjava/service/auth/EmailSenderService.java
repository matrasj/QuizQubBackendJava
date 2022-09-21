package com.example.quizqubbackendjava.service.auth;

import com.example.quizqubbackendjava.model.entity.User;
import lombok.RequiredArgsConstructor;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
    private final JavaMailSender javaMailSender;

    @Async
    public void sendConfirmationEmail(User user, String token) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("jkob.matras@gmail.com");
        simpleMailMessage.setSubject("Account confirmation");
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setText("Hello"
        + "\nConfirm your email here --> http://localhost:8081/api/v1/auth/confirm?token=" + token);
        javaMailSender.send(simpleMailMessage);
    }
}
