package com.example.quizqubbackendjava.service.auth;



import com.example.quizqubbackendjava.exception.*;
import com.example.quizqubbackendjava.model.entity.ConfirmationToken;
import com.example.quizqubbackendjava.model.entity.Role;
import com.example.quizqubbackendjava.model.entity.User;
import com.example.quizqubbackendjava.model.payload.auth.login.LoginPayloadRequest;
import com.example.quizqubbackendjava.model.payload.auth.login.LoginPayloadResponse;
import com.example.quizqubbackendjava.model.payload.auth.registration.RegistrationPayloadRequest;
import com.example.quizqubbackendjava.model.payload.auth.registration.RegistrationPayloadResponse;
import com.example.quizqubbackendjava.repository.ConfirmationTokenRepository;
import com.example.quizqubbackendjava.repository.RoleRepository;
import com.example.quizqubbackendjava.repository.UserRepository;
import com.example.quizqubbackendjava.validation.EmailValidator;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final String EMAIL_NOT_VALID_MESSAGE = "Email %s is not valid";
    private static final String USER_ALREADY_EXISTS_MESSAGE = "User with username %s already exists";
    private static final String NOT_FOUND_TOKEN_MESSAGE = "Not found token --> %s";
    private static final String TOKEN_ALREADY_EXPIRED_MESSAGE = "Token %s has already expired";
    private static final boolean ENABLE_ACCOUNT = true;
    private static final String TOKEN_ALREADY_CONFIRMED_MESSAGE = "Token %s has been already confirmed at %s";
    private static final String SUCCESSFULLY_TOKEN_CONFIRMATION = "Successfully confirmed token";
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailValidator emailValidator;
    private final EmailSenderService emailSenderService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public RegistrationPayloadResponse registerUser(RegistrationPayloadRequest request) {

        String email = request.getEmail();
        if (!emailValidator.test(email)) {
            throw new EmailNotValidException(String.format(EMAIL_NOT_VALID_MESSAGE, email));
        }

        String username = request.getUsername();
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException(String.format(USER_ALREADY_EXISTS_MESSAGE, username));
        }

        Role userRole = roleRepository.findByName("STUDENT");
        ConfirmationToken confirmationToken = ConfirmationTokenFactory.createConfirmationToken();

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(username)
                .password(passwordEncoder.encode(request.getPassword()))
                .email(email)
                .role(userRole)
                .tokens(new HashSet<>())
                .build();

        confirmationToken.setUser(user);
        user.getTokens().add(confirmationToken);

        userRepository.save(user);
        confirmationTokenRepository.save(confirmationToken);
        roleRepository.save(userRole);

        emailSenderService.sendConfirmationEmail(user, confirmationToken.getToken());

        return new RegistrationPayloadResponse(confirmationToken.getToken());
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException(String.format(NOT_FOUND_TOKEN_MESSAGE, token)));

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new TokenAlreadyExpiredException(String.format(TOKEN_ALREADY_EXPIRED_MESSAGE, token));
        }

        if (confirmationToken.getConfirmedAt() != null) {
            throw new TokenAlreadyConfirmedException(String.format(TOKEN_ALREADY_CONFIRMED_MESSAGE,
                    token,
                    confirmationToken.getConfirmedAt().toString()));
        }

        confirmationToken.getUser().setEnabled(ENABLE_ACCOUNT);
        confirmationToken.setConfirmedAt(LocalDateTime.now());

        confirmationTokenRepository.save(confirmationToken);

        return SUCCESSFULLY_TOKEN_CONFIRMATION;
    }

    public LoginPayloadResponse loginUser(LoginPayloadRequest loginPayloadRequest) {
        return jwtService.createJwtToken(loginPayloadRequest);
    }
}
