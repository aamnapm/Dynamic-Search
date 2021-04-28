package com.example.search.controller;

import com.example.search.dto.PersonDTO;
import com.example.search.mapper.PersonMapper;
import com.example.search.model.Person;
import com.example.search.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/person")
public class PersonController extends SearchController<Person, PersonDTO.Info> {

    @Autowired
    private PersonMapper personMapper;

    public PersonController(ISearchService<Person> iNICICOSearchService) {
        super(iNICICOSearchService);
    }

    @Override
    protected List<PersonDTO.Info> toDTOInfo(List<Person> list) {
        return personMapper.toDTOInfo(list);
    }
}
