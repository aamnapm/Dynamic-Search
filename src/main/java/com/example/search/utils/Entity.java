package com.example.search.utils;

import com.example.search.dto.FieldTypeDTO;
import com.example.search.service.ISearchService;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.persistence.Column;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
            for (Annotation annotation : declaredAnnotations) {
                if (annotation instanceof Column && declaredAnnotations.length == 1) {
                    FieldTypeDTO fieldTypeDTO = new FieldTypeDTO();
                    fieldTypeDTO.setName(field.getName());
                    fieldTypeDTO.setType(field.getType());

                    fieldTypeDTOS.add(fieldTypeDTO);
                }
            }
        }

        return fieldTypeDTOS;
    }
}
