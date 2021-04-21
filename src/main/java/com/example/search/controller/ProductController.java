package com.example.search.controller;

import com.example.search.model.Product;
import com.example.search.service.ISearchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/product")
public class ProductController extends SearchController<Product> {

    public ProductController(ISearchService<Product> iNICICOSearchService) {
        super(iNICICOSearchService);
    }
}
