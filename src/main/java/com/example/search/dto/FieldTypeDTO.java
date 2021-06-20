package com.example.search.dto;

import com.example.search.enums.EOperator;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldTypeDTO {
    private String type;
    private String name;
    private String entity;
    private List<EOperator> typeList;
}
