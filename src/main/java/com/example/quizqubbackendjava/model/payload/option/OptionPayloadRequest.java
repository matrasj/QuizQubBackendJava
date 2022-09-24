package com.example.quizqubbackendjava.model.payload.option;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OptionPayloadRequest {
    private String content;
}
