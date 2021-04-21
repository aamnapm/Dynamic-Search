package com.example.search.repository;

import com.example.search.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends SearchRepository<Product, String> {
}