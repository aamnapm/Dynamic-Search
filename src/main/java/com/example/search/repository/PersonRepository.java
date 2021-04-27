package com.example.search.repository;

import com.example.search.model.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends SearchRepository<Person, Long> {
}