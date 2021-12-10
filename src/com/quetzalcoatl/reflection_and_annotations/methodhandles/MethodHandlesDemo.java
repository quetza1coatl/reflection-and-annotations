package com.quetzalcoatl.reflection_and_annotations.methodhandles;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;

public class MethodHandlesDemo {
    public static void main(String[] args) throws Throwable {
       Lookup lookup = MethodHandles.lookup();
       Class<?> clazz = Student.class;
          /* Use in Java 9+  */
//        Class<?> clazz = lookup.findClass(Student.class);

        Student s = new Student();
        s.setCourse("Python");

        // Invoke certain method
        MethodType methodType = MethodType.methodType(String.class);
        MethodHandle getCourse = lookup.findVirtual(Student.class, "getCourse", methodType);
        Object result = getCourse.invoke(s);
        System.out.println(result);

       // Constructor
        MethodType methodTypeConstr = MethodType.methodType(void.class);
        MethodHandle noArgConstructor = lookup.findConstructor(clazz, methodTypeConstr);
        Student student = (Student) noArgConstructor.invoke();
        student.setCourse("M1");
        student.setName("Billy");
        System.out.println(student);

        MethodType methodTypeConstr2 = MethodType.methodType(void.class, String.class, String.class);
        MethodHandle paramConstructor = lookup.findConstructor(clazz, methodTypeConstr2);
        Student student2 = (Student) paramConstructor.invoke("Josh", "C++");
        System.out.println(student2);


    }
}
