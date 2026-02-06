package com.devcaotics.airBnTruta.misc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class FormHandler {

    public List<String> getAttributeNames(@SuppressWarnings("rawtypes") Class clazz){
        //estratégia com os campos de um objeto
        /*Field fields[] = clazz.getDeclaredFields();
        
        List<String> names = Arrays.stream(fields)
            .map(Field::getName)
            .collect(Collectors.toList());*/

        Method[] methods = clazz.getDeclaredMethods();

        List<String> names = Arrays.stream(methods)
            .filter(m -> m.getName().startsWith("set"))
            .map(Method::getName)
            .map(m -> m.substring(3))
            .collect(Collectors.toList());

        return names;
    }

    public Object getAttributeValue(Object o, String fieldName){

        Method[] methods = o.getClass().getDeclaredMethods();

        Method method = Arrays.stream(methods)
            .filter(m -> m.getName().equalsIgnoreCase("get"+fieldName))
            .findFirst().orElse(null);

        try {
            Object oReturn = method.invoke(o);
            return oReturn;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

}
