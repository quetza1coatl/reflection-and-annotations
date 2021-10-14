package com.quetzalcoatl.reflection_and_annotations.reflection;

public class GettingClassObject {
    public static void main(String[] args) throws ClassNotFoundException {
        //Class#forName
        Class<?> clazz1 = Class.forName("java.lang.String");
        Class<?> clazz2 = Class.forName("java.lang.String");
        System.out.println(clazz1==clazz2);

        // ClassName.class, works wit primitives too
        Class<?> clazz3 = int.class;
        Class<?> clazz4 = String.class;

        // obj#getClass
        MyClass myClass = new MyClass();
        Class<? extends MyClass> clazz5 = myClass.getClass();

        //superclass
        Class<?> superclass = clazz1.getSuperclass();

        //interfaces
        Class<?>[] interfaces = clazz1.getInterfaces();

        //name
        System.out.println(clazz1.getName());
    }
}

class MyClass{

    MyClass() {
        System.out.println("MyClass object created!");
    }

}