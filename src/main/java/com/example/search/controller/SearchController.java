package com.example.search.controller;

import com.example.search.dto.FieldTypeDTO;
import com.example.search.dto.SectionDTO;
import com.example.search.service.ISearchService;
import com.example.search.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@RequiredArgsConstructor
public abstract class SearchController<T, D> {

    protected final ISearchService<T, D> iNICICOSearchService;

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
    public ResponseEntity<List<D>> search(@RequestBody SectionDTO sectionDTO) {
        return new ResponseEntity<>(iNICICOSearchService.search(sectionDTO, new Utils().find(iNICICOSearchService)), HttpStatus.OK);
    }

    @PostMapping(value = "/fields")
    public ResponseEntity<List<FieldTypeDTO>> getFields() {
        return new ResponseEntity<>(iNICICOSearchService.getFields(tType), HttpStatus.OK);
    }

    @PostMapping(value = "/config")
    public ResponseEntity<List<FieldTypeDTO>> getConfig() {
        return new ResponseEntity<>(iNICICOSearchService.getConfig(), HttpStatus.OK);
    }

}
