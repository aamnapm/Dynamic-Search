package com.example.search.controller;

import com.example.search.dto.SectionDTO;
import com.example.search.mapper.SectionMapper;
import com.example.search.model.Address;
import com.example.search.model.Product;
import com.example.search.service.ISearchAddressService;
import com.example.search.service.ISearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/search")
public class SearchController {

    private final SectionMapper sectionMapper;
    private final ISearchService iSearchService;
    private final ISearchAddressService iSearchAddressService;

    @PostMapping
    public ResponseEntity<List<Product>> search(@RequestBody SectionDTO sectionDTO) {
        return new ResponseEntity<>(iSearchService.search(sectionMapper.toSection(sectionDTO)), HttpStatus.OK);
    }

    @PostMapping(value = "/a")
    public ResponseEntity<List<Address>> searchA(@RequestBody SectionDTO sectionDTO) {
        return new ResponseEntity<>(iSearchAddressService.searchA(sectionMapper.toSection(sectionDTO)), HttpStatus.OK);
    }

}
