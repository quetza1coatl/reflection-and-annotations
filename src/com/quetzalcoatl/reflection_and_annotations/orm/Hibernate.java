package com.quetzalcoatl.reflection_and_annotations.orm;

import com.quetzalcoatl.reflection_and_annotations.orm.annotations.Column;
import com.quetzalcoatl.reflection_and_annotations.orm.annotations.PrimaryKey;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Hibernate<T> {
    private Connection con;
    private AtomicLong id = new AtomicLong(0);

    private Hibernate() throws SQLException {
//        con = DriverManager.getConnection("");
    }


    public static <T> Hibernate<T> getConnection() throws SQLException {
        return new Hibernate<T>();
    }

    public void write(T t) throws IllegalAccessException, SQLException {
        Class<?> clazz = t.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();

        Field pKey = null;
        List<Field> columns = new ArrayList<>();
        StringJoiner joiner = new StringJoiner(",");

        for(Field field : declaredFields){
            field.setAccessible(true);
            if(field.isAnnotationPresent(PrimaryKey.class)){
                pKey = field;
                joiner.add(pKey.getName());
                System.out.println(String.format("Primary key field: %s, value=%d", pKey.getName(), field.get(t)));
            }
            if(field.isAnnotationPresent(Column.class)){
                columns.add(field);
                joiner.add(field.getName());
                System.out.println("Column field: " + field.getName() + ", value=" + field.get(t));
            }
        }
        int quantity = columns.size() + 1;
        String qMarks = IntStream.range(0, quantity)
                .mapToObj(e -> "?")
                .collect(Collectors.joining(","));

        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)",
                clazz.getSimpleName(), joiner.toString(), qMarks);
        System.out.println(sql);
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        if(pKey.getType() == Long.class){
            preparedStatement.setLong(1, id.incrementAndGet());
        }
        int index = 2;
        for(Field column : columns){
            column.setAccessible(true);
            if(column.getType() == Integer.class){
                preparedStatement.setInt(index++, (int) column.get(t));
            } else if(column.getType() == String.class){
                preparedStatement.setString(index++, (String) column.get(t));
            } else if(column.getType() == double.class){
                preparedStatement.setDouble(index++, (Double) column.get(t));
            }
        }
        preparedStatement.execute();
    }
}
