package com.example.search.utils;

import com.example.search.dto.FieldTypeDTO;
import com.example.search.service.ISearchService;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Entity {

    public <T> Object find(ISearchService<T> iNICICOSearchService) {
        Type[] actualTypeArguments = ((ParameterizedTypeImpl) iNICICOSearchService.getClass().getGenericSuperclass()).getActualTypeArguments();

        for (Type type : actualTypeArguments) {
            try {
                if (!((Class) type).getName().contains("java.lang")) {
                    return ((Class) type).newInstance();
                }

            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
//                throw ;
                return null;
            }
        }
        //                throw ;
        return null;
    }

    public List<FieldTypeDTO> findEntityFieldsName(Class clazz) {
        List<FieldTypeDTO> fieldTypeDTOS = new ArrayList<>();
        return findEntityFieldsName(clazz, fieldTypeDTOS, null);
    }

    public List<FieldTypeDTO> findEntityFieldsName(Class clazz, List<FieldTypeDTO> fieldTypeDTOS, String entity) {

        List<String> duplicate = findDuplicate(clazz);

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
            for (Annotation annotation : declaredAnnotations) {

                if (annotation instanceof JoinColumn) {
                    String type = findType(field);
                    fieldTypeDTOS = findEntityFieldsName(field.getType(), fieldTypeDTOS, type);
                }

                if (annotation instanceof Column && declaredAnnotations.length == 1) {
                    getClassFields(field, fieldTypeDTOS, entity, duplicate);
                }
            }
        }

        return fieldTypeDTOS;
    }

    private List<FieldTypeDTO> getClassFields(Field field, List<FieldTypeDTO> fieldTypeDTOS, String entity, List<String> duplicate) {
        Column value = field.getAnnotation(Column.class);

        List<String> collect = duplicate.stream().filter(s -> s.equals(value.name())).collect(Collectors.toList());

        if (collect.size() > 0) {
            return fieldTypeDTOS;
        } else {
            fieldTypeDTOS.add(createFieldTypeDTO(field, entity));
        }

        return fieldTypeDTOS;
    }

    private List<String> findDuplicate(Class clazz) {
        List<String> duplicateList = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            Column value = field.getAnnotation(Column.class);
            if (value != null) {
                duplicateList.add(value.name());
            }
            JoinColumn value2 = field.getAnnotation(JoinColumn.class);
            if (value2 != null) {
                duplicateList.add(value2.name());
            }
        }

        return duplicateList.stream().filter(i -> Collections.frequency(duplicateList, i) > 1).collect(Collectors.toList());
    }

    private FieldTypeDTO createFieldTypeDTO(Field field, String entity) {
        FieldTypeDTO fieldTypeDTO = new FieldTypeDTO();
        fieldTypeDTO.setName(field.getName());
        String type = findType(field);
        fieldTypeDTO.setType(type);
        fieldTypeDTO.setEntity(entity);
        return fieldTypeDTO;
    }

    private String findType(Field field) {
        String[] split = field.getType().getName().split("\\.");
        return split[split.length - 1];
    }
}
