package com.quetzalcoatl.reflection_and_annotations.annotations;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MostUsed {
    String value() default "Java";
}
