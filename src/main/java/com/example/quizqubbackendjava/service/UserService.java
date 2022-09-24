package com.example.quizqubbackendjava.service;

import com.example.quizqubbackendjava.exception.UserNotFoundException;
import com.example.quizqubbackendjava.mapper.UserPayloadResponseMapper;
import com.example.quizqubbackendjava.model.entity.Role;
import com.example.quizqubbackendjava.model.entity.User;
import com.example.quizqubbackendjava.model.payload.user.UserPayloadRequest;
import com.example.quizqubbackendjava.model.payload.user.UserPayloadResponse;
import com.example.quizqubbackendjava.repository.RoleRepository;
import com.example.quizqubbackendjava.repository.SessionRepository;
import com.example.quizqubbackendjava.repository.SubjectRepository;
import com.example.quizqubbackendjava.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final boolean ENABLE_ACCOUNT = true;
    private static final String SUCCESSFULLY_CREATED_USER = "Successfully created user";
    private static final String USER_NOT_FOUND_MESSAGE = "Not found user with ID --> %d";
    private static final String SUCCESSFULLY_UPDATED_USER = "Successfully updated user";
    private static final int MIN_PASSWORD_LENGTH = 4;
    private static final String SUCCESSFULLY_DELETED_USER = "Successfully deleted user";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final SubjectRepository subjectRepository;
    private final SessionRepository sessionRepository;

    @PostConstruct
    @Transactional
    public void initRoles() {
        Role studentRole = Role.builder()
                .name("STUDENT")
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
                .role(studentRole)
                .email("chris.volkanowic@gmail.com")
                .enabled(ENABLE_ACCOUNT)
                .build();

        userRepository.save(adminJacob);
        userRepository.save(teacherWalter);
        userRepository.save(userChris);


        roleRepository.saveAll(List.of(studentRole, teacherRole, adminRole));

    }

    public Page<UserPayloadResponse> findUsersWithPagination(int pageNumber, int pageSize) {
        Page<User> usersPage = userRepository.findAll(PageRequest.of(pageNumber, pageSize));

        return new PageImpl<>(
                usersPage
                        .stream()
                        .map(UserPayloadResponseMapper::mapToUserPayloadResponse)
                        .collect(Collectors.toList()),
                PageRequest.of(pageNumber, pageSize),
                usersPage.getTotalElements()
        );
    }

    @Transactional
    public String createUser(UserPayloadRequest userPayloadRequest) {
        User builtUser = buildUserFromPayload(userPayloadRequest);
        builtUser.setPassword(passwordEncoder.encode(userPayloadRequest.getPassword()));
        userRepository.save(builtUser);

        return SUCCESSFULLY_CREATED_USER;
    }

    @Transactional
    public String updateUser(UserPayloadRequest userPayloadRequest, Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, userId)));
        Role roleByName = roleRepository.findByName(userPayloadRequest.getRoleName().toUpperCase(Locale.ROOT));

        existingUser.setFirstName(userPayloadRequest.getFirstName());
        existingUser.setLastName(userPayloadRequest.getLastName());
        existingUser.setUsername(userPayloadRequest.getUsername());
        existingUser.setEmail(userPayloadRequest.getEmail());
        existingUser.setRole(roleByName);
        if (userPayloadRequest.getPassword() != null && userPayloadRequest.getPassword().length() >= MIN_PASSWORD_LENGTH) {
            existingUser.setPassword(passwordEncoder.encode(userPayloadRequest.getPassword()));
        }

        userRepository.save(existingUser);

        return SUCCESSFULLY_UPDATED_USER;
    }

    private User buildUserFromPayload(UserPayloadRequest userPayloadRequest) {
        Role roleByName = roleRepository.findByName(userPayloadRequest.getRoleName().toUpperCase(Locale.ROOT));
        User build = User.builder()
                .firstName(userPayloadRequest.getFirstName())
                .lastName(userPayloadRequest.getLastName())
                .username(userPayloadRequest.getUsername())
                .email(userPayloadRequest.getEmail())
                .enabled(ENABLE_ACCOUNT)
                .role(roleByName)
                .build();

        roleByName.getUsers().add(build);

        return build;
    }

    public UserPayloadResponse getUserDataById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, userId)));

        return UserPayloadResponseMapper.mapToUserPayloadResponse(user);
    }

    @Transactional
    public String deleteUserById(Long userId) {
        System.out.println(userId);
        userRepository.deleteById(userId);
        
        return SUCCESSFULLY_DELETED_USER;
    }


    public Map<String, Integer> findAttemptsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, userId)));

        Map<String, Integer> attempts = new HashMap<>();

        subjectRepository.findAll()
                .forEach((subject -> {
                    if (!attempts.containsKey(subject.getName())) {
                        attempts.put(subject.getName(), 0);
                    }
                }));

        sessionRepository.findAll()
                .stream()
                .filter((session -> session.getUser().equals(user)))
                .forEach((session -> {
                    String subjectName = session.getSubject().getName();
                    attempts.replace(subjectName, attempts.get(subjectName), attempts.get(subjectName) + 1);
                }));

        return attempts;
    }
}
