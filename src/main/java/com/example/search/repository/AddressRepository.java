package com.example.search.repository;

import com.example.search.model.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends SearchRepository<Address, Long> {
}