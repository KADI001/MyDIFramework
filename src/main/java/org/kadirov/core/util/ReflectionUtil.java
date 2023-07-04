package org.kadirov.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtil {

    public static Method getGetterMethodOfField(Field field){
        try {
            String methodName = getGetterOrSetterMethodName(FieldMethod.Getter, field.getName());
            return field.getDeclaringClass().getMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static Method getSetterMethodOfField(Field field){
        try {
            String methodName = getGetterOrSetterMethodName(FieldMethod.Setter, field.getName());
            return field.getDeclaringClass().getMethod(methodName, field.getType());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getGetterOrSetterMethodName(FieldMethod method, String fieldName){
        String methodName = "";

        switch (method){
            case Getter -> methodName = "get";
            case Setter -> methodName = "set";
            default -> methodName = "null";
        }

        char firstCharLower = fieldName.toLowerCase().charAt(0);
        char firstCharUpper = fieldName.toUpperCase().charAt(0);

        char secondCharLower = fieldName.toLowerCase().charAt(1);
        char secondCharUpper = fieldName.toUpperCase().charAt(1);

        boolean isFirstCharLetter = firstCharUpper != firstCharLower;
        boolean isSecondCharLetter = secondCharLower != secondCharUpper;


        if(fieldName.length() >= 2){

            if(isFirstCharLetter && isSecondCharLetter){
                if(fieldName.charAt(0) == fieldName.toLowerCase().charAt(0) &&
                        fieldName.charAt(1) == fieldName.toUpperCase().charAt(1)) {
                    methodName += fieldName;
                }
            }

            methodName += firstCharUpper + fieldName.substring(1);
        }

        return methodName;
    }

    public static <T extends Annotation> Map<T, Field> getAllFieldsWithAnnotation(Class<?> classType, Class<T> annotationClass){
        Map<T, Field> fieldMap = new HashMap<>();

        Field[] reflectListFields = classType.getDeclaredFields();
        for (Field reflectListField : reflectListFields) {
            T annotation = reflectListField.getAnnotation(annotationClass);
            if(annotation != null){
                fieldMap.put(annotation, reflectListField);
            }
        }

        Class<?> superClass = classType.getSuperclass();
        while (superClass != null){
            Map<T, Field> sFieldMap = getAllFieldsWithAnnotation(superClass, annotationClass);
            fieldMap.putAll(sFieldMap);
            superClass = superClass.getSuperclass();
        }

        return fieldMap;
    }

    public static boolean extendsOf(Class<?> classType, Class<?> parent){
        Class<?> superClass = classType.getSuperclass();
        while (superClass != null){
            if(superClass == parent){
                return true;
            }
            superClass = superClass.getSuperclass();
        }

        return false;
    }

    public enum FieldMethod {
        Getter,
        Setter
    }
}
