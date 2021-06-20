package com.example.search.controller;

import com.example.search.dto.FieldTypeDTO;
import com.example.search.dto.SectionDTO;
import com.example.search.mapper.CommonMapper;
import com.example.search.service.ISearchService;
import com.example.search.utils.Entity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@RequiredArgsConstructor
//@RestController
public abstract class SearchController<T, D> {

    protected final ISearchService<T> iNICICOSearchService;
    protected CommonMapper<T, D> commonMapper;

    private Class<T> tType;
    private Class<D> dType;

    {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        //noinspection unchecked
        tType = (Class<T>) superClass.getActualTypeArguments()[0];
        //noinspection unchecked
        dType = (Class<D>) superClass.getActualTypeArguments()[1];
    }

    @PostMapping(value = "/search")
    public ResponseEntity<List<T>> search(@RequestBody SectionDTO sectionDTO) {
        return new ResponseEntity<>(iNICICOSearchService.search(sectionDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/test")
    public ResponseEntity<List<D>> test(@RequestBody SectionDTO sectionDTO) {
        return new ResponseEntity<>(toDTOInfo(iNICICOSearchService.test(sectionDTO, new Entity().find(iNICICOSearchService))), HttpStatus.OK);
    }

    @PostMapping(value = "/fields")
    public ResponseEntity<List<FieldTypeDTO>> getFields() {
        return new ResponseEntity<>(iNICICOSearchService.getFields(tType), HttpStatus.OK);
    }

    @PostMapping(value = "/config")
    public ResponseEntity<List<FieldTypeDTO>> getConfig() {
        return new ResponseEntity<>(iNICICOSearchService.getConfig(), HttpStatus.OK);
    }

    protected List<D> toDTOInfo(List<T> list) {
        return commonMapper.toDTOInfo(list);
    }
}
