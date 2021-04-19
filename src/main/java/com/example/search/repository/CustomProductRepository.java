package com.example.search.repository;


import com.example.search.enums.ECondition;
import com.example.search.model.Product;
import com.example.search.model.Rule;
import com.example.search.model.Section;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
@RequiredArgsConstructor
public class CustomProductRepository {
    //    private static final Double PREMIUM_PRICE = 1000D;
    private final ProductRepository productRepository;

 /*   public List<Product> getLowRangeProducts(List<Category> categories) {
        return productRepository.findAll(where(belongsToCategory(categories)).and(pricesAreBetween(0D, PREMIUM_PRICE)));
    }

    public List<Product> getPremiumProducts(List<Category> categories) {
        return productRepository.findAll(
                where(belongsToCategory(categories)).and(isPremium()));
    }

    public List<Product> getPremiumProducts(String name, List<Category> categories) {
        return productRepository.findAll(
                where(belongsToCategory(categories))
                        .and(nameLike(name))
                        .and(isPremium()));
    }*/


    public List<Product> getData(Section section) {

        if (section == null) {
            return productRepository.findAll();
            //throw exception
        } else {
            Specification<Product> specification = getRules(section);
            if (specification != null) {
                return productRepository.findAll(specification);
            } else {
                return productRepository.findAll();
            }
        }
    }

    private Specification<Product> getRules(Section section) {
        return getQueryResult(section.getRules(), section.getCondition());
    }

//    public List<Product> getRules(Section section) {
//        return getQueryResult(section.getRules(), section.getCondition());
//    }

    public Specification<Product> getQueryResult(List<Rule> rules, ECondition ECondition) {
        if (rules.size() > 0) {
            return getSpecificationFromFilters(rules, ECondition);
        } else {
            return null;
        }
    }
//
//    public List<Product> getQueryResult(List<Rule> rules, Condition condition) {
//        if (rules.size() > 0) {
//            return productRepository.findAll(getSpecificationFromFilters(rules, condition));
//        } else {
//            return productRepository.findAll();
//        }
//    }

    private Specification<Product> getSpecificationFromFilters(List<Rule> rules, ECondition ECondition) {
        Specification<Product> specification = where(
                createSpecification(rules.remove(0))
        );
        for (Rule input : rules) {
            if (ECondition == ECondition.AND)
                specification = specification.and(createSpecification(input));
            else
                specification = specification.or(createSpecification(input));
        }
        return specification;
    }

    private Specification<Product> checkSection(Rule rule) {
//        if (rule.getSection() == null) {
        return createSpecification(rule);
//        } else {
//            List<Product> rules = getRules(rule.getSection());
//        }
    }

    private Specification<Product> createSpecification(Rule input) {
        switch (input.getOperator()) {
            case EQUALS:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(input.getField()),
                                castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case NOT_EQ:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.notEqual(root.get(input.getField()),
                                castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case GREATER_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.gt(root.get(input.getField()),
                                (Number) castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case LESS_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.lt(root.get(input.getField()),
                                (Number) castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case LIKE:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(input.getField()), "%" + input.getValue() + "%");
            case IN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.in(root.get(input.getField()))
                                .value(castToRequiredType(root.get(input.getField()).getJavaType(), input.getValues()));
            default:
                throw new RuntimeException("Operation not supported yet");
        }
    }

    private Object castToRequiredType(Class fieldType, String value) {
        if (fieldType.isAssignableFrom(Double.class)) {
            return Double.valueOf(value);
        } else if (fieldType.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(value);
        } else if (Enum.class.isAssignableFrom(fieldType)) {
            return Enum.valueOf(fieldType, value);
        }
        return null;
    }

    private Object castToRequiredType(Class fieldType, List<String> value) {
        List<Object> lists = new ArrayList<>();
        for (String s : value) {
            lists.add(castToRequiredType(fieldType, s));
        }
        return lists;
    }

  /*  private Specification<Product> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), "%" + name + "%");
    }


    private Specification<Product> pricesAreBetween(Double min, Double max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(Product_.PRICE), min, max);
    }

    private Specification<Product> belongsToCategory(List<Category> categories) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(Product_.CATEGORY)).value(categories);
    }

    private Specification<Product> isPremium() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(Product_.MANUFACTURING_PLACE).get(Address_.STATE),
                                STATE.CALIFORNIA),
                        criteriaBuilder.greaterThanOrEqualTo(root.get(Product_.PRICE), PREMIUM_PRICE));
    }*/
}