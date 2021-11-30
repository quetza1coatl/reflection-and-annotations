package com.quetzalcoatl.reflection_and_annotations.annotations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReadingAnnotations {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = Class.forName("com.quetzalcoatl.reflection_and_annotations.annotations.Utils");
        Constructor<?> constructor = clazz.getConstructor();
        Utils utils = (Utils) constructor.newInstance();

        Method[] methods = clazz.getDeclaredMethods();
        for(Method m : methods){
            if(m.isAnnotationPresent(MostUsed.class)){
                MostUsed annotation = m.getAnnotation(MostUsed.class);
                m.invoke(utils, annotation.value());
            }
        }
    }
}
