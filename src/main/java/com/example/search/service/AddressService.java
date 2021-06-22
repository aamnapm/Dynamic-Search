package com.example.search.service;

import com.example.search.dto.AddressDTO;
import com.example.search.mapper.AddressMapper;
import com.example.search.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AddressService extends SearchService<Address, Long, AddressDTO.Info> {

    @Autowired
    protected AddressMapper personMapper;

    @Override
    protected List<AddressDTO.Info> toDTOInfo(List<Address> list) {
        return personMapper.toDTOInfo(list);
    }
}
