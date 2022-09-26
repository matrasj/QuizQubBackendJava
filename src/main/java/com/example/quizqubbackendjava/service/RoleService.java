package com.example.quizqubbackendjava.service;

import com.example.quizqubbackendjava.mapper.RolePayloadResponseMapper;
import com.example.quizqubbackendjava.model.payload.role.RolePayloadResponse;
import com.example.quizqubbackendjava.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<RolePayloadResponse> findRoles() {
        return roleRepository.findAll()
                .stream()
                .map(RolePayloadResponseMapper::mapToRolePayloadResponse)
                .collect(Collectors.toList());

    }
}
