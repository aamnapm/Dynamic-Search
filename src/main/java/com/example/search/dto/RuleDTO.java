package com.example.search.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RuleDTO {
    private String value;
    private String field;
    private String operator;
    private List<String> values;//Used in case of IN operator

    private String condition;
    private List<RuleDTO> rules;
}