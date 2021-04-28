package com.example.search.controller;

import com.example.search.dto.AddressDTO;
import com.example.search.mapper.AddressMapper;
import com.example.search.model.Address;
import com.example.search.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/address")
public class AddressController extends SearchController<Address, AddressDTO.Info> {

    @Autowired
    private AddressMapper addressMapper;

    public AddressController(ISearchService<Address> iNICICOSearchService) {
        super(iNICICOSearchService);
    }

    @Override
    protected List<AddressDTO.Info> toDTOInfo(List<Address> list) {
        return addressMapper.toDTOInfo(list);
    }
}
