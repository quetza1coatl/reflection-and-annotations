package com.quetzalcoatl.reflection_and_annotations.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInfo {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Entity e = new Entity(5, "z");
        Class<? extends Entity> clazz = e.getClass();
        Method[] methods = clazz.getMethods();
        for(Method method: methods){
            System.out.println(method.getName());
        }

        Method[] declaredMethods = clazz.getDeclaredMethods();

        Method method = clazz.getMethod("setVal", int.class);
        method.invoke(e, 33);

        Method method2 = clazz.getMethod("getVal", null);
        System.out.println(method2.invoke(e, null));
    }
}
