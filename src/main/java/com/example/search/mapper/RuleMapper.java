package com.example.search.mapper;

import com.example.search.dto.RuleDTO;
import com.example.search.enums.EOperator;
import com.example.search.model.Rule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RuleMapper {

    @Mappings({
            @Mapping(source = "operator", target = "operator", qualifiedByName = "toEnum")
    })
    Rule toRule(RuleDTO ruleDTO);


    @Named("toEnum")
    default EOperator toEnum(String str) {
        return EOperator.valueOf(str.toUpperCase());
    }
}
