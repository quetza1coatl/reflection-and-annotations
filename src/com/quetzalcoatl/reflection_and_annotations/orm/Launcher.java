package com.quetzalcoatl.reflection_and_annotations.orm;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Launcher {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // create DB for the first time
//        Class.forName("org.h2.Driver");
//        Connection conn = DriverManager.getConnection("jdbc:h2:D:\\Projects\\reflection-and-annotations\\database\\h2-hib", "sa", "");

        // run H2 server
        Server.main();
    }
}
