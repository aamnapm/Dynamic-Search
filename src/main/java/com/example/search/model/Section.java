package com.example.search.model;

import com.example.search.enums.ECondition;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Section {
    private Section section;
    private List<Rule> rules;
    private ECondition condition;
}
