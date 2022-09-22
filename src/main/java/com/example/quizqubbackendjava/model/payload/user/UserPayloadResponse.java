package com.example.quizqubbackendjava.model.payload.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserPayloadResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private boolean enabled;
    private Date createdAt;
    private Date lastUpdated;
    private String roleName;
}
