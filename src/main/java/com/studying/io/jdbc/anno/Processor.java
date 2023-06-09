package com.studying.io.jdbc.anno;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

// creating table using annotations
public class Processor {
    private static Connection connection;
    private static Statement statement;

    public static void main(String[] args) {
        try {
            connect();
            buildTable(Cat.class);
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    public static void buildTable(Class cl) throws SQLException {
        if (!cl.isAnnotationPresent(Table.class)) {
            throw new RuntimeException("@Table missed");
        }
        Map<Class, String> hm = new HashMap<>();
        hm.put(int.class, "INTEGER");
        hm.put(String.class, "TEXT");
        // CREATE TABLE cats (id INTEGER, name TEXT, age INTEGER) ;
        StringBuilder stringBuilder = new StringBuilder("CREATE TABLE ");
        stringBuilder.append(((Table)cl.getAnnotation(Table.class)).title());
        stringBuilder.append(" (");
        // 'CREATE TABLE cats ('
        Field[] fields = cl.getDeclaredFields();
        for (Field o : fields) {
            if (o.isAnnotationPresent(Column.class)) {
                stringBuilder.append(o.getName())
                        .append(" ")
                        .append(hm.get(o.getType()))
                        .append(", ");
            }
        }
        // 'CREATE TABLE cats (id INTEGER, name TEXT, age INTEGER, '
        stringBuilder.setLength(stringBuilder.length() - 2);
        // 'CREATE TABLE cats (id INTEGER, name TEXT, age INTEGER'
        stringBuilder.append(");");
        // 'CREATE TABLE cats (id INTEGER, name TEXT, age INTEGER);'

        statement.executeUpdate(stringBuilder.toString());
    }

    public static void disconnect() {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            // Class.forName("org.postgresql.Driver"); if postgresql
            connection = DriverManager.getConnection("jdbc:sqlite:main3.db");
            statement = connection.createStatement();

        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Unable to connect");
        }
    }
}
