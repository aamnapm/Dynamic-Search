package com.example.search.controller;

import com.example.search.dto.SectionDTO;
import com.example.search.mapper.CommonMapper;
import com.example.search.service.ISearchService;
import com.example.search.utils.Entity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
//@RestController
public abstract class SearchController<T, D> {

    protected final ISearchService<T> iNICICOSearchService;
    protected CommonMapper<T, D> commonMapper;

    @PostMapping(value = "/search")
    public ResponseEntity<List<T>> search(@RequestBody SectionDTO sectionDTO) {
        return new ResponseEntity<>(iNICICOSearchService.search(sectionDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/test")
    public ResponseEntity<List<D>> test(@RequestBody SectionDTO sectionDTO) {
        return new ResponseEntity<>(toDTOInfo(iNICICOSearchService.test(sectionDTO, new Entity().find(iNICICOSearchService))), HttpStatus.OK);
    }

    protected List<D> toDTOInfo(List<T> list) {
        return commonMapper.toDTOInfo(list);
    }
}
