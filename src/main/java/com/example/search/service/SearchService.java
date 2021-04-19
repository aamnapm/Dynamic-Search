package com.example.search.service;

import com.example.search.model.Product;
import com.example.search.model.Section;
import com.example.search.repository.CustomProductRepository;
import com.example.search.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SearchService implements ISearchService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> search(Section section) {

        CustomProductRepository customProductRepository = new CustomProductRepository(productRepository);
        List<Product> queryResult = customProductRepository.getData(section);
//        List<Product> queryResult = customProductRepository.getQueryResult(rules);

        System.out.println("result=>" + queryResult.size());
        queryResult.forEach(product -> {
            System.out.println("result=>" + product.getName());
        });

        return queryResult;
    }
}
