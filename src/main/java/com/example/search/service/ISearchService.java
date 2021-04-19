package com.example.search.service;

import com.example.search.model.Product;
import com.example.search.model.Section;

import java.util.List;

public interface ISearchService {

    List<Product> search(Section section);

}
