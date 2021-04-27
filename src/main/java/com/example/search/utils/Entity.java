package com.example.search.utils;

import com.example.search.service.ISearchService;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Type;

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
}
