package com.quetzalcoatl.reflection_and_annotations.annotations;

import java.util.ArrayList;

class Parent{
    public void m1() {
        System.out.println(" m1 Parent Implementation");
    }

    @Deprecated
    public void m2(int a) {
        System.out.println(" m2 Parent Implementation, a is : "+ a);
    }
}

public class GeneralPurpose extends Parent {

    @Override
    public void m1() {
        System.out.println("m1 Child Implementation");
    }

    public static void main(String[] args) {

        int a = 10;

        @SuppressWarnings({"rawtypes", "unused"})
        ArrayList aList = new ArrayList();

        GeneralPurpose obj = new GeneralPurpose();
        obj.m2(a);

        Integer i = new Integer(0);

        MyFunctionalInterface impl = () -> System.out.println("Method invoked");
        impl.method();
    }
}

@FunctionalInterface
interface MyFunctionalInterface{
    void method();
}
