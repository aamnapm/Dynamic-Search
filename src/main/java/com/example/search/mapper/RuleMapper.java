package com.example.search.mapper;

import com.example.search.dto.RuleDTO;
import com.example.search.enums.ECondition;
import com.example.search.enums.EOperator;
import com.example.search.model.Rule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RuleMapper {

    @Mappings({
            @Mapping(source = "operator", target = "operator", qualifiedByName = "toOperatorEnum"),
            @Mapping(source = "condition", target = "condition", qualifiedByName = "toConditionEnum")
    })
    Rule toRule(RuleDTO ruleDTO);


    @Named("toOperatorEnum")
    default EOperator toOperatorEnum(String str) {
        return EOperator.valueOf(str.toUpperCase());
    }

    List<Rule> toRuleList(List<RuleDTO> sectionDTOS);

    @Named("toConditionEnum")
    default ECondition toConditionEnum(String str) {
        return ECondition.valueOf(str.toUpperCase());
    }
}
