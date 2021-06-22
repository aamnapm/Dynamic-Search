package com.example.search.service;

import com.example.search.dto.FieldTypeDTO;
import com.example.search.dto.SectionDTO;
import com.example.search.enums.ECondition;
import com.example.search.enums.EFieldOperator;
import com.example.search.exeption.BadRequestException;
import com.example.search.mapper.CommonMapper;
import com.example.search.mapper.SectionMapper;
import com.example.search.model.Rule;
import com.example.search.model.Section;
import com.example.search.repository.SearchRepository;
import com.example.search.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.JoinColumn;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

public abstract class SearchService<T, ID extends Serializable, D> implements ISearchService<T, D> {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public SearchRepository<T, ID> repository;

    @Autowired
    protected SectionMapper sectionMapper;

    protected CommonMapper<T, D> commonMapper;

    @Override
    public List<D> search(SectionDTO section, Object t) {
        String key = getKey(t);
        System.out.println(key);

        List<T> queryResult = getData(map(section), key);

        System.out.println("result=>" + queryResult.size());
        queryResult.forEach(product -> {
            System.out.println("result=>" + product);
        });

        return toDTOInfo(queryResult);
    }

    protected List<T> getData(Section section, String key) {
        if (section == null) {
            return repository.findAll();
        } else {
            Specification<T> specification1 = where(null);
            Specification<T> specification = getSpecification(section, key, specification1);
            if (specification != null) {
                return repository.findAll(specification);
            } else {
                return repository.findAll();
            }
        }
    }

    private String getKey(Object t) {

        String key = null;
        Field[] declaredFields = t.getClass().getDeclaredFields();

        for (Field method : declaredFields) {
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
            for (Annotation annotation : declaredAnnotations) {
                if (annotation instanceof JoinColumn) {
                    String[] split = method.getType().toString().split("\\.");
                    key = split[split.length - 1].toLowerCase();
                }
            }
        }

        if (key != null) {
            return key;
        } else {
            throw new BadRequestException("Key can not be null!");
        }
    }

    protected Specification<T> getSpecification(Section section, String key, Specification<T> specification1) {
        return getSpecification(section.getRules(), section.getCondition(), key, specification1);
    }

    protected Specification<T> getSpecification(List<Rule> rules, ECondition ECondition, String key, Specification<T> specification1) {
        specification1 = where(null);
        if (rules.size() > 0) {
            return checkCondition(rules, ECondition, key, specification1);
        } else {
            throw new BadRequestException("List can not be null!");
        }
    }

    private Specification<T> checkCondition(List<Rule> rules, ECondition condition, String key, Specification<T> specification) {

        if (condition == ECondition.AND) {
            specification = Specification.where(specification.and(checkRules(rules, condition, key, specification)));
        } else {
            specification = Specification.where(specification.or(checkRules(rules, condition, key, specification)));
        }
        return specification;
    }

    private Specification<T> checkRules(List<Rule> rules, ECondition condition, String key, Specification<T> specification) {
        for (Rule input : rules) {
            if (condition == ECondition.AND) {
                System.out.println(condition);
                if (input.getCondition() == null) {
                    specification = specification.and(createSpecification(input, key));
                } else {
                    specification = specification.and(getSpecification(input.getRules(), input.getCondition(), key, specification));
                }
            } else {
                System.out.println(condition);
                if (input.getCondition() == null) {
                    specification = specification.or(createSpecification(input, key));
                } else {
                    specification = specification.or(getSpecification(input.getRules(), input.getCondition(), key, specification));
                }
            }
        }
        return specification;
    }

    private Specification<T> createSpecification(Rule input, String key) {
        System.out.println(input.getValue());
        String[] split = input.getField().split("\\.");
        if (split.length >= 2) {
            String s = split[0];
            if (key.equals(s)) {
                return checkOperatorAndCreateSpecification(input, key);
            } else {
                throw new BadRequestException("Foreign Key inCorrect!");
            }
        } else {
            return checkOperatorAndCreateSpecification(input);
        }
    }

    protected Specification<T> checkOperatorAndCreateSpecification(Rule input) {
        switch (input.getOperator()) {
            case GREATER_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.gt(root.get(input.getField()), (Number) castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));

            case LESS_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.lt(root.get(input.getField()), (Number) castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));

            case GREATER_THAN_EQUAL:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.ge(root.get(input.getField()), (Number) castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));

            case LESS_THAN_EQUAL:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.le(root.get(input.getField()), (Number) castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));

            case BETWEEN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.between(root.get(input.getField()), input.getDual().getFirstValue(), input.getDual().getSecondValue());

            case EQUALS:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(input.getField()), castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));

            case NOT_EQ:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.notEqual(root.get(input.getField()), castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));

            case LIKE:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(input.getField()), "%" + input.getValue() + "%");

            case NOT_LIKE:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.notLike(root.get(input.getField()), input.getValue());

            case iS_NULL:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.isNull(root.get(input.getField()));

            case iS_NOT_NULL:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.isNotNull(root.get(input.getField()));
            case IN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.in(root.get(input.getField())).value(castToRequiredType(root.get(input.getField()).getJavaType(), input.getValues()));
            default:
                throw new RuntimeException("Operation not supported yet");
        }
    }

    protected Specification<T> checkOperatorAndCreateSpecification(Rule input, String key) {
        String[] split = input.getField().split("\\.");
        switch (input.getOperator()) {

            case GREATER_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.gt(root.get(input.getField()), (Number) castToRequiredType(root.join(key).get(input.getField()).getJavaType(), input.getValue()));

            case LESS_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.lt(root.get(input.getField()), (Number) castToRequiredType(root.join(key).get(input.getField()).getJavaType(), input.getValue()));

            case GREATER_THAN_EQUAL:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.ge(root.get(input.getField()), (Number) castToRequiredType(root.join(key).get(input.getField()).getJavaType(), input.getValue()));

            case LESS_THAN_EQUAL:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.le(root.get(input.getField()), (Number) castToRequiredType(root.join(key).get(input.getField()).getJavaType(), input.getValue()));

            case BETWEEN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.between(root.join(key).get(input.getField()), input.getDual().getFirstValue(), input.getDual().getSecondValue());

            case EQUALS:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(input.getField()), castToRequiredType(root.join(key).get(input.getField()).getJavaType(), input.getValue()));

            case NOT_EQ:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.notEqual(root.get(input.getField()), castToRequiredType(root.join(key).get(input.getField()).getJavaType(), input.getValue()));

            case LIKE:
                return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.join(key).get(split[split.length - 1]), "%" + input.getValue() + "%");

            case NOT_LIKE:
                return (root, query, criteriaBuilder) -> criteriaBuilder.notLike(root.join(key).get(split[split.length - 1]), input.getValue());

            case iS_NULL:
                return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.join(key).get(input.getField()));

            case iS_NOT_NULL:
                return (root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.join(key).get(input.getField()));

            case IN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.in(root.get(input.getField())).value(castToRequiredType(root.join(key).get(input.getField()).getJavaType(), input.getValues()));

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
        throw new BadRequestException("cast field");
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

    @Override
    public List<FieldTypeDTO> getFields(Class clazz) {
        return new Utils().findEntityFieldsName(clazz);
    }

    @Override
    public List<FieldTypeDTO> getConfig() {
        List<FieldTypeDTO> fieldTypeDTOS = new ArrayList<>();
        for (EFieldOperator eFieldOperator : EFieldOperator.values()) {
            FieldTypeDTO fieldTypeDTO = new FieldTypeDTO();
            String[] split = eFieldOperator.getType().getName().split("\\.");
            fieldTypeDTO.setType(split[split.length - 1]);
            fieldTypeDTO.setTypeList(eFieldOperator.getOperatorList());
            fieldTypeDTOS.add(fieldTypeDTO);
        }
        return fieldTypeDTOS;
    }

    protected List<D> toDTOInfo(List<T> list) {
        return commonMapper.toDTOInfo(list);
    }
}
