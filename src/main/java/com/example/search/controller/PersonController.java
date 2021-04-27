package com.example.search.controller;

import com.example.search.model.Person;
import com.example.search.service.ISearchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/person")
public class PersonController extends SearchController<Person> {

    public PersonController(ISearchService<Person> iNICICOSearchService) {
        super(iNICICOSearchService);
    }
}
