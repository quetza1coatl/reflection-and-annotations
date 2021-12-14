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

        MethodType methodTypeSetName = MethodType.methodType(void.class, String.class);
        MethodHandle setName = lookup.findVirtual(clazz, "setName", methodTypeSetName);
        setName.invoke(student2, "Josh_updated");
        System.out.println(student2);

       // static method
        MethodType methodTypeSetStatic = MethodType.methodType(void.class, int.class);
        MethodHandle setNumOfStudents = lookup.findStatic(clazz, "setNumOfStudents", methodTypeSetStatic);
        setNumOfStudents.invoke(66);
        System.out.println(Student.getNumOfStudents());

        //fields
        Lookup privateLookup = MethodHandles.privateLookupIn(clazz, lookup); // Java 9+
        MethodHandle findNameGetter = privateLookup.findGetter(clazz, "name", String.class);
        MethodHandle findNameSetter = privateLookup.findSetter(clazz, "name", String.class);

        String name = (String) findNameGetter.invoke(student2);
        System.out.println(name);
        findNameSetter.invoke(student, "Billy-20");
        System.out.println(student.getName());

        //VarHandles (Java 9+)
        VarHandle courseVarHandle = privateLookup.findVarHandle(clazz, "course", String.class);
        courseVarHandle.set(student, "Kotlin");
        String val = (String)courseVarHandle.get();
        System.out.println(val);


    }
}
