package com.example.search.model;

import com.example.search.enums.EOperator;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Rule {
    private String value;
    private String field;
    private EOperator operator;
    private List<String> values;//Used in case of IN operator
}
