package com.quetzalcoatl.reflection_and_annotations.spring;

import com.quetzalcoatl.reflection_and_annotations.spring.annotations.Autowired;
import com.quetzalcoatl.reflection_and_annotations.spring.annotations.Component;
import com.quetzalcoatl.reflection_and_annotations.spring.annotations.ComponentScan;
import com.quetzalcoatl.reflection_and_annotations.spring.annotations.Configuration;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    // context with class object - instances of classes
    private static Map<Class<?>, Object> context = new HashMap<>();

    public ApplicationContext(Class<AppConfig> clazz) {
        Spring.initializeSpringContext(clazz);
    }

    private static class Spring{

        private static void initializeSpringContext(Class<?> clazz) {
            if(!clazz.isAnnotationPresent(Configuration.class)){
                throw new RuntimeException("File is not a configuration file: " + clazz.getSimpleName());
            }else{
                ComponentScan annotation = clazz.getAnnotation(ComponentScan.class);
                String value = annotation.value();
                //TODO make it more independent
                String packageStructure = "D:\\Projects\\reflection-and-annotations\\out\\production\\reflection-and-annotations\\" + value.replace(".", "\\");
                File[] files = findClasses(new File(packageStructure));

                for(File file : files){
                    String name = value + "." + file.getName().replace(".class", "");
                    try {
                        Class<?> loadedClass = Class.forName(name);
                        if(loadedClass.isAnnotationPresent(Component.class)){
                            Constructor<?> constructor = loadedClass.getConstructor();
                            Object newInstance = constructor.newInstance();
                            context.put(loadedClass, newInstance);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private static File[] findClasses(File file){
            if(!file.exists()){
                throw new RuntimeException("Package " + file + " doesn't exist.");
            }
            return file.listFiles(f -> f.getName().endsWith(".class"));
        }
    }

    public <T> T getBean(Class<T> clazz) throws IllegalAccessException {
        T object = (T) context.get(clazz);
        Field[] declaredFields = clazz.getDeclaredFields();
        injectBean(object, declaredFields);
        return object;
    }

    private <T> void injectBean(T object, Field[] declaredFields) throws IllegalAccessException {
        for(Field field : declaredFields){
            if(field.isAnnotationPresent(Autowired.class)){
                field.setAccessible(true);
                Class<?> type = field.getType();
                Object injectedObject = context.get(type);
                field.set(object, injectedObject);

                //recursively inject beans
                Field[] declaredFieldsForInjectedObject = type.getDeclaredFields();
                injectBean(injectedObject, declaredFieldsForInjectedObject);
            }
        }
    }
}
