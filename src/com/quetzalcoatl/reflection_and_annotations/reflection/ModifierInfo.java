package com.quetzalcoatl.reflection_and_annotations.reflection;

import java.lang.reflect.Modifier;

public class ModifierInfo {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clazz = Class.forName("com.quetzalcoatl.reflection_and_annotations.reflection.Entity");
        int modifiers = clazz.getModifiers();
        System.out.println(modifiers & Modifier.PUBLIC);
        System.out.println(Modifier.isPrivate(modifiers));
        String stringModifiers = Modifier.toString(modifiers);
        System.out.println(stringModifiers);
    }
}
