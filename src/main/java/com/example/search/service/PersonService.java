package com.example.search.service;

import com.example.search.dto.PersonDTO;
import com.example.search.mapper.PersonMapper;
import com.example.search.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService extends SearchService<Person, Long, PersonDTO.Info> {

    @Autowired
    protected PersonMapper personMapper;

    @Override
    protected List<PersonDTO.Info> toDTOInfo(List<Person> list) {
        return personMapper.toDTOInfo(list);
    }

}
