package com.example.quizqubbackendjava.mapper;

import com.example.quizqubbackendjava.model.entity.Role;
import com.example.quizqubbackendjava.model.payload.role.RolePayloadResponse;

public class RolePayloadResponseMapper {
    public static RolePayloadResponse mapToRolePayloadResponse(Role role) {
        return RolePayloadResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .roleDescription(role.getRoleDescription())
                .build();
    }
}
