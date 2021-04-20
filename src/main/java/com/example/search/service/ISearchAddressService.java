package com.example.search.service;

import com.example.search.model.Address;
import com.example.search.model.Section;

import java.util.List;

public interface ISearchAddressService {

    List<Address> searchA(Section section);

}
