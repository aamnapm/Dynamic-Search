package com.example.search.mapper;

import com.example.search.dto.RuleDTO;
import com.example.search.dto.SectionDTO;
import com.example.search.enums.ECondition;
import com.example.search.model.Rule;
import com.example.search.model.Section;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SectionMapper {

    @Mappings({
            @Mapping(source = "condition", target = "condition", qualifiedByName = "toEnum")
    })
    Section toSection(SectionDTO sectionDTO);


    List<Rule> toRuleList(List<RuleDTO> sectionDTOS);

    @Named("toEnum")
    default ECondition toEnum(String str) {
        return ECondition.valueOf(str.toUpperCase());
    }
}
