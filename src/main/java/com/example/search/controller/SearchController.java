package com.example.search.controller;

import com.example.search.dto.SectionDTO;
import com.example.search.mapper.SectionMapper;
import com.example.search.model.Product;
import com.example.search.service.ISearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/search")
public class SearchController {

    private final SectionMapper sectionMapper;
    private final ISearchService iSearchService;

    @PostMapping
    public ResponseEntity<List<Product>> search(@RequestBody SectionDTO sectionDTO) {

      /*  List<RuleDTO> ruleDTOS = new ArrayList<>();
        RuleDTO ruleDTO = RuleDTO.builder()
                .field("name")
                .operator("LIKE")
                .value("zara")
                .build();

        RuleDTO ruleDTO2 = RuleDTO.builder()
                .field("name")
                .operator("LIKE")
                .value("Sony")
                .build();

        RuleDTO ruleDTO3 = RuleDTO.builder()
                .field("name")
                .operator("LIKE")
                .value("100")
                .build();

        ruleDTOS.add(ruleDTO);
        ruleDTOS.add(ruleDTO2);
        ruleDTOS.add(ruleDTO3);


        List<RuleDTO> rules2 = new ArrayList<>();
        RuleDTO ruleDTO4 = RuleDTO.builder()
                .field("price")
                .operator("EQUALS")
                .value("100")
                .build();
        rules2.add(ruleDTO4);

        SectionDTO sectionDTO2 = SectionDTO.builder().condition("AND").rules(rules2).build();


        SectionDTO sectionDTO1 = SectionDTO.builder().condition("OR").rules(ruleDTOS).build();

//        section.setSection(section2);

*/
        return new ResponseEntity<>(iSearchService.search(sectionMapper.toSection(sectionDTO)), HttpStatus.OK);
    }

}
