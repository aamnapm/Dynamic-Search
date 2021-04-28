package com.example.search.mapper;

import com.example.search.dto.AddressDTO;
import com.example.search.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper extends CommonMapper<Address, AddressDTO.Info> {
}
