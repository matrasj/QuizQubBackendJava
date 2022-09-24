package com.example.quizqubbackendjava.model.payload.option;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OptionPayloadResponse {
    private Long id;
    private String content;

}
