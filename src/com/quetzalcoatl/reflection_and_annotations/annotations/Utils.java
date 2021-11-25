package com.quetzalcoatl.reflection_and_annotations.annotations;

@MostUsed
public class Utils {


    void doStuff(){
        System.out.println("Doing something");
    }

    @MostUsed("Python")
    void doStuff(String value){
        System.out.println("Operationg with:" + value);
    }

    void doStuff(int value){
        System.out.println("Operationg with:" + value);
    }

}
