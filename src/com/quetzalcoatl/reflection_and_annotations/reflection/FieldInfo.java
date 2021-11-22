package com.quetzalcoatl.reflection_and_annotations.reflection;

import java.lang.reflect.Field;

public class FieldInfo {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Entity entity = new Entity(10, "id");
        Class<? extends Entity> clazz = entity.getClass();

        final Field[] fields = clazz.getFields();
        for(Field f : fields){
            System.out.println(f.getName());
        }

        final Field[] declaredFields = clazz.getDeclaredFields();
        for(Field f : declaredFields){
            System.out.println(f.getAnnotatedType().getType());
        }

        Field field = clazz.getField("type");
        field.set(entity, "newValue");
        System.out.println(entity.getType());

        Field privateField = clazz.getDeclaredField("val");
        privateField.setAccessible(true);
        privateField.set(entity, 55);
        System.out.println(entity.getVal());

    }
}
