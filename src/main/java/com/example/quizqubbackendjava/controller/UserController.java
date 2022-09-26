package com.example.quizqubbackendjava.controller;

import com.example.quizqubbackendjava.model.payload.user.UserPayloadRequest;
import com.example.quizqubbackendjava.model.payload.user.UserPayloadResponse;
import com.example.quizqubbackendjava.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/pagination")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserPayloadResponse>> getUsersWithPagination(@RequestParam int pageNumber,
                                                                            @RequestParam int pageSize) {
        return ResponseEntity.status(OK)
                .body(userService.findUsersWithPagination(pageNumber, pageSize));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createUser(@RequestBody UserPayloadRequest userPayloadRequest) {
        return ResponseEntity.status(CREATED)
                .body(userService.createUser(userPayloadRequest));
    }

    @PutMapping("/update/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateUser(@RequestBody UserPayloadRequest userPayloadRequest,
                                             @PathVariable Long userId) {
        return ResponseEntity.status(ACCEPTED)
                .body(userService.updateUser(userPayloadRequest, userId));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserPayloadResponse> getUserById(@PathVariable Long userId) {
        return ResponseEntity.status(OK)
                .body(userService.getUserDataById(userId));
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        return ResponseEntity.status(OK)
                .body(userService.deleteUserById(userId));
    }


    @GetMapping("/findAttemptsByUserId")
    public ResponseEntity<Map<String, Integer>> findAttempts(@RequestParam Long userId) {
        return ResponseEntity.status(OK)
                .body(userService.findAttemptsByUserId(userId));
    }

    @GetMapping("/findByRoleName")
    public ResponseEntity<Page<UserPayloadResponse>> getUserWithPaginationByRoleName(@RequestParam int pageSize,
                                                                                      @RequestParam int pageNumber,
                                                                                      @RequestParam String roleName) {
        return ResponseEntity.status(OK)
                .body(userService.findUserWithPaginationByRoleName(roleName, pageSize, pageNumber));
    }




}
