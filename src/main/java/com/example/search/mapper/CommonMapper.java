package com.example.search.mapper;

import java.util.List;

public interface CommonMapper<T, D> {

    D toDTO(T Address);

    T toEntity(D AddressDTO);

    List<D> toDTOInfo(List<T> eList);
}
