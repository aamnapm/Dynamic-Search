package com.example.search.service;

import com.example.search.dto.SectionDTO;

import java.util.List;

public interface ISearchService<T> {
    List<T> search(SectionDTO section);

    List<T> test(SectionDTO section, Object t);
}
