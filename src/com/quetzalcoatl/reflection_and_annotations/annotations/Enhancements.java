package com.quetzalcoatl.reflection_and_annotations.annotations;

public class Enhancements {

    public static void main(String[] args) {
        Box<String> box = new @NonEmpty @ReadOnly Box<>(10, "Container");
        box.new @ReadOnly NestedBox(11, "Cylinder");
    }
}
