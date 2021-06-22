package com.example.search.controller;

import com.example.search.dto.AddressDTO;
import com.example.search.model.Address;
import com.example.search.service.ISearchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/address")
public class AddressController extends SearchController<Address, AddressDTO.Info> {

    public AddressController(ISearchService<Address, AddressDTO.Info> iNICICOSearchService) {
        super(iNICICOSearchService);
    }
}
