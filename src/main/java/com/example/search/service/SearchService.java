package com.example.search.service;

import com.example.search.dto.SectionDTO;
import com.example.search.enums.ECondition;
import com.example.search.mapper.SectionMapper;
import com.example.search.model.Rule;
import com.example.search.model.Section;
import com.example.search.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.JoinColumn;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

public abstract class SearchService<T, ID extends Serializable> implements ISearchService<T> {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public SearchRepository<T, ID> repository;

    @Autowired
    protected SectionMapper sectionMapper;

    @Override
    public List<T> search(SectionDTO section) {
        List<T> queryResult = getData(map(section), "");

        //todo:for develop
        System.out.println("result=>" + queryResult.size());
        queryResult.forEach(product -> {
            System.out.println("result=>" + product);
        });

        return queryResult;
    }

    @Override
    public List<T> test(SectionDTO section, Object t) {
        String key = getKey(t);
        System.out.println(key);

        List<T> queryResult = getData(map(section), key);

        System.out.println("result=>" + queryResult.size());
        queryResult.forEach(product -> {
            System.out.println("result=>" + product);
        });

        return queryResult;
//        return "";
    }

    private String getKey(Object t) {

        String key = null;
        Field[] declaredFields = t.getClass().getDeclaredFields();

        for (Field method : declaredFields) {
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
            for (Annotation annotation : declaredAnnotations) {
               /* if (annotation instanceof OneToMany) {
                    System.out.println(method.getName() + " OneToMany " + annotation);
                }
                if (annotation instanceof ManyToMany) {
                    System.out.println(method.getName() + " ManyToMany " + annotation);
                }
                if (annotation instanceof ManyToOne) {
                    System.out.println(method.getName() + " ManyToOne " + annotation);
                }*/
                if (annotation instanceof JoinColumn) {
//                    System.out.println(method.getName() + " JoinColumn ");

                    String[] split = method.getType().toString().split("\\.");

                    key = split[split.length - 1].toLowerCase();

//                    System.out.println("key " + key);

//                    System.out.println(((JoinColumn) annotation).name());
                }
            }
        }

        if (key != null) {
            return key;
        } else {
            //throw
            return null;
        }
    }

    protected List<T> getData(Section section, String key) {
        if (section == null) {
            return repository.findAll();
        } else {
            Specification<T> specification = getSpecification(section, key);
            if (specification != null) {
                return repository.findAll(specification);
            } else {
                return repository.findAll();
            }
        }
    }

    protected Specification<T> getSpecification(Section section, String key) {
        return getSpecification(section.getRules(), section.getCondition(), key);
    }

    protected Specification<T> getSpecification(List<Rule> rules, ECondition ECondition, String key) {
        if (rules.size() > 0) {
            return checkCondition(rules, ECondition, key);
        } else {
            return null;//throws exception
        }
    }

    private Specification<T> checkCondition(List<Rule> rules, ECondition condition, String key) {
        Rule firstRule = rules.remove(0);
        Specification<T> specification = null;

        if (firstRule.getCondition() == null) {
            specification = where(createSpecification(firstRule, key));
        } else {
            specification = getSpecification(firstRule.getRules(), firstRule.getCondition(), key);//todo
        }

        for (Rule input : rules) {
            if (condition == ECondition.AND) {
                if (input.getCondition() == null) {
                    specification = specification.and(createSpecification(input, key));
                } else {
                    specification = getSpecification(input.getRules(), input.getCondition(), key);//todo
                }
            } else {
                if (input.getCondition() == null) {
                    specification = specification.or(createSpecification(input, key));
                } else {
                    specification = getSpecification(input.getRules(), input.getCondition(), key);//todo
                }
            }
        }
        return specification;
    }

    private Specification<T> createSpecification(Rule input, String key) {
        String[] split = input.getField().split("\\.");
        if (split.length >= 2) {
            String s = split[0];
            if (key.equals(s)) {
                System.out.println("=> " + s);
                System.out.println("=>> " + input.getField());
            }
            return checkOperatorAndCreateSpecification(input, key);
        } else {
            return checkOperatorAndCreateSpecification(input);
        }
    }

    protected Specification<T> checkOperatorAndCreateSpecification(Rule input) {
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

    protected Specification<T> checkOperatorAndCreateSpecification(Rule input, String key) {
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
                return (root, query, criteriaBuilder) -> {
                    String[] split = input.getField().split("\\.");
                    return criteriaBuilder.like(root.join(key).get(split[split.length - 1]), "%" + input.getValue() + "%");
                };
            case IN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.in(root.get(input.getField()))
                                .value(castToRequiredType(root.get(input.getField()).getJavaType(), input.getValues()));
            default:
                throw new RuntimeException("Operation not supported yet");
        }
    }

    private Specification<T> join(Rule input) {
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

    private Section map(SectionDTO sectionDTO) {
        return sectionMapper.toSection(sectionDTO);
    }
}
