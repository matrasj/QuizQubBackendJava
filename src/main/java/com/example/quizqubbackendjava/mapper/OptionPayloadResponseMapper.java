package com.example.quizqubbackendjava.mapper;

import com.example.quizqubbackendjava.model.entity.Option;
import com.example.quizqubbackendjava.model.payload.option.OptionPayloadResponse;

public class OptionPayloadResponseMapper {
    public static OptionPayloadResponse mapToOptionPayloadResponse(Option option) {
        return OptionPayloadResponse.builder()
                .id(option.getId())
                .content(option.getContent())
                .build();
    }
}
