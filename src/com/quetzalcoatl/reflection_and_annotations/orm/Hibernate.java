package com.quetzalcoatl.reflection_and_annotations.orm;

import com.quetzalcoatl.reflection_and_annotations.orm.annotations.Column;
import com.quetzalcoatl.reflection_and_annotations.orm.annotations.PrimaryKey;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
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
        con = DriverManager.getConnection("jdbc:h2:D:\\Projects\\reflection-and-annotations\\database\\h2-hib", "sa", "");
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
            }
        }
        preparedStatement.execute();
    }

    public T read(Class<T> clazz, long id) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Field[] declaredFields = clazz.getDeclaredFields();
        Field pKey = null;
        for(Field field : declaredFields){
            if(field.isAnnotationPresent(PrimaryKey.class)){
                pKey = field;
                break;
            }
        }
        if(pKey == null){
            throw new RuntimeException("Can't find Primary key for the entity: " + clazz.getSimpleName());
        }
        String sql = String.format("SELECT * FROM %s WHERE %s=%s", clazz.getSimpleName(), pKey.getName(), id);
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        // cursor is initially positioned before the first row; the first call to the method next
        // makes the first row the current row
        resultSet.next();
        T result = clazz.getConstructor().newInstance();

        // set id to object
        long transactionId = resultSet.getInt(pKey.getName());
        pKey.setAccessible(true);
        pKey.set(result, transactionId);

        // set other fields to object
        for(Field field : declaredFields){
            if(field.isAnnotationPresent(Column.class)){
                field.setAccessible(true);
                if(field.getType() == Integer.class){
                    field.set(result, resultSet.getInt(field.getName()));
                }else if(field.getType() == String.class){
                    field.set(result, resultSet.getString(field.getName()));
                }
            }
        }
        return result;
    }
}
