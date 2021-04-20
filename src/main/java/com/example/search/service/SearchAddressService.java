package com.example.search.service;

import com.example.search.enums.ECondition;
import com.example.search.model.Address;
import com.example.search.model.Rule;
import com.example.search.model.Section;
import com.example.search.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@AllArgsConstructor
@Service
public class SearchAddressService implements ISearchAddressService {

    private final AddressRepository addressRepository;

    @Override
    public List<Address> searchA(Section section) {

        List<Address> queryResult = getData(section);

        //todo:for develop
        System.out.println("result=>" + queryResult.size());
        queryResult.forEach(Address -> {
            System.out.println("result=>" + Address.getCity());
        });

        return queryResult;
    }

    private List<Address> getData(Section section) {
        if (section == null) {
            return addressRepository.findAll();
        } else {
            Specification<Address> specification = getSpecification(section);
            if (specification != null) {
                return addressRepository.findAll(specification);
            } else {
                return addressRepository.findAll();
            }
        }
    }

    private Specification<Address> getSpecification(Section section) {
        return getSpecification(section.getRules(), section.getCondition());
    }

    public Specification<Address> getSpecification(List<Rule> rules, ECondition ECondition) {
        if (rules.size() > 0) {
            return checkCondition(rules, ECondition);
        } else {
            return null;//throws exception
        }
    }

    private Specification<Address> checkCondition(List<Rule> rules, ECondition condition) {
        Rule firstRule = rules.remove(0);
        Specification<Address> specification = null;

        if (firstRule.getCondition() == null) {
            specification = where(createSpecification(firstRule));
        } else {
            specification = getSpecification(firstRule.getRules(), firstRule.getCondition());//todo
        }

        for (Rule input : rules) {
            if (condition == ECondition.AND) {
                if (input.getCondition() == null) {
                    specification = specification.and(createSpecification(input));
                } else {
                    specification = getSpecification(input.getRules(), input.getCondition());//todo
                }
            } else {
                if (input.getCondition() == null) {
                    specification = specification.or(createSpecification(input));
                } else {
                    specification = getSpecification(input.getRules(), input.getCondition());//todo
                }
            }
        }
        return specification;
    }


    private Specification<Address> createSpecification(Rule input) {
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
}
