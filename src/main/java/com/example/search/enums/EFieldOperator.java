package com.example.search.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public enum EFieldOperator {
    LONG(Long.class, Arrays.asList(EOperator.GREATER_THAN, EOperator.LESS_THAN)),
    STRING(String.class, Arrays.asList(EOperator.EQUALS, EOperator.LIKE, EOperator.NOT_EQ));


    private static final Map<Class, EFieldOperator> BY_TYPE = new HashMap<>();
    private static final Map<List<String>, EFieldOperator> BY_OPERATOR = new HashMap<>();

    static {
        for (EFieldOperator e : values()) {
            BY_TYPE.put(e.type, e);
        }
    }

    private Class type;
    private List<EOperator> operatorList;


    EFieldOperator(Class type, List<EOperator> operatorList) {
        this.type = type;
        this.operatorList = operatorList;
    }

    public static EFieldOperator valueOfOperators(String operator) {
        return BY_OPERATOR.get(operator);
    }

    public static EFieldOperator valueOfType(Class aClass) {
        return BY_TYPE.get(aClass);
    }
}
