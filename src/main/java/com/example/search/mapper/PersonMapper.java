package com.example.search.mapper;

import com.example.search.dto.PersonDTO;
import com.example.search.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper extends CommonMapper<Person, PersonDTO.Info> {
}
