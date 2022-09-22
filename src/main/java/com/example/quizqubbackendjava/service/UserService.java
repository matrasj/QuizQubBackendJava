package com.example.quizqubbackendjava.service;

import com.example.quizqubbackendjava.model.entity.Role;
import com.example.quizqubbackendjava.model.entity.User;
import com.example.quizqubbackendjava.repository.RoleRepository;
import com.example.quizqubbackendjava.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final boolean ENABLE_ACCOUNT = true;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void initRoles() {
        Role userRole = Role.builder()
                .name("USER")
                .roleDescription("Role for every student in application. He can does quizzes")
                .build();

        Role teacherRole = Role.builder()
                .name("TEACHER")
                .roleDescription("Role for teachers. Teachers can create quizzes.")
                .build();

        Role adminRole = Role.builder()
                .name("ADMIN")
                .roleDescription("Admins can manipulate with accounts and delete users either as teachers")
                .build();

        User adminJacob = User.builder()
                .firstName("Jakub")
                .lastName("Matras")
                .username("pozerinhooo")
                .password(passwordEncoder.encode("Czajnik13!"))
                .role(adminRole)
                .email("jkob.matras@gmail.com")
                .enabled(ENABLE_ACCOUNT)
                .build();

        User teacherWalter = User.builder()
                        .firstName("Walter")
                        .lastName("White")
                        .username("teacher")
                        .password(passwordEncoder.encode("teacher"))
                        .role(teacherRole)
                        .email("walter.white@gmail.com")
                        .enabled(ENABLE_ACCOUNT)
                        .build();

        User userChris = User.builder()
                .firstName("Chris")
                .lastName("Volkanowic")
                .username("student")
                .password(passwordEncoder.encode("student"))
                .role(userRole)
                .email("chris.volkanowic@gmail.com")
                .enabled(ENABLE_ACCOUNT)
                .build();

        userRepository.save(adminJacob);
        userRepository.save(teacherWalter);
        userRepository.save(userChris);

        roleRepository.saveAll(List.of(userRole, teacherRole, adminRole));

    }
}
