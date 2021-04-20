package com.example.search.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SectionDTO {
    private String condition;
    private List<RuleDTO> rules;
}
