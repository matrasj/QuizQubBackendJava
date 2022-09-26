package com.example.quizqubbackendjava.model.payload.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RolePayloadResponse {
    private Long id;
    private String name;
    private String roleDescription;
}
