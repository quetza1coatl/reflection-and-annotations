package com.quetzalcoatl.reflection_and_annotations.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConstructorInfo {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = Class.forName("com.quetzalcoatl.reflection_and_annotations.reflection.Entity");
        Constructor<?>[] constructors = clazz.getConstructors();
        for(Constructor<?> c : constructors){
            System.out.println(c);
        }

        final Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();

        Constructor<?> constructor = clazz.getConstructor(int.class, String.class);
        Entity e = (Entity) constructor.newInstance(31,"IUI");
    }
}
