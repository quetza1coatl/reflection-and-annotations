package com.quetzalcoatl.reflection_and_annotations.spring.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(ComponentScans.class)
public @interface ComponentScan {
    String value();
}
