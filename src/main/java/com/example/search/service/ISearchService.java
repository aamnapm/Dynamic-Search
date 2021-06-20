package com.example.search.service;

import com.example.search.dto.FieldTypeDTO;
import com.example.search.dto.SectionDTO;

import java.util.List;

public interface ISearchService<T> {
    List<T> search(SectionDTO section);

    List<T> test(SectionDTO section, Object t);

    List<FieldTypeDTO> getFields(Class clazz);

    List<FieldTypeDTO> getConfig();
}
