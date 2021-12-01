package com.quetzalcoatl.reflection_and_annotations.orm;

import com.quetzalcoatl.reflection_and_annotations.orm.annotations.Column;
import com.quetzalcoatl.reflection_and_annotations.orm.annotations.PrimaryKey;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Hibernate<T> {
    private Connection con;

    private Hibernate() throws SQLException {
//        con = DriverManager.getConnection("");
    }


    public static <T> Hibernate<T> getConnection() throws SQLException {
        return new Hibernate<T>();
    }

    public void write(T t) throws IllegalAccessException {
        Class<?> clazz = t.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for(Field field : declaredFields){
            field.setAccessible(true);
            if(field.isAnnotationPresent(PrimaryKey.class)){
                System.out.println(String.format("Primary key field: %s, value=%d", field.getName(), field.get(t)));
            }
            if(field.isAnnotationPresent(Column.class)){
                System.out.println("Column field: " + field.getName() + ", value=" + field.get(t));
            }
        }
    }
}
