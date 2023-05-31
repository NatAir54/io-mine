package com.studying.io.jdbc_test;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class JdbcTest {
    private static final String URL = "jdbc:sqlite:users.db";
    private static final String CREATE_TABLE_SQL = "CREATE TABLE Users(ID INT PRIMARY KEY NOT NULL, NAME TEXT NOT NULL, AGE INT NOT NULL)";

    private static final String SAVE_USER_SQL_PREFIX = "INSERT INTO Users(ID, NAME, AGE) VALUES(";

    public static void main(String[] args) throws SQLException {
        //createNewDatabase();
        //createTableUsers();
        //fillTableUsers();
        User userFound = findUserByName("Pasha");
        System.out.println(userFound);
    }

    public static User findUserByName(String name) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, name, age FROM Users WHERE name = '" + name + "';");
            if(!resultSet.next()) {
                return null;
            }

            return new User(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("age"));
        }
    }

    public static void fillTableUsers() throws SQLException {
        List<User> users = Arrays.asList(new User(1, "Sveta", 18), new User(2, "Dima", 19),
                new User(3, "Pasha", 23), new User(4, "Artem", 32));
        for(User user : users) {
            saveUser(user);
        }
    }

    public static void saveUser(User user) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement statement = conn.createStatement();
            statement.execute(SAVE_USER_SQL_PREFIX + user.id + ", '" + user.name + "', " + user.age + ");");
        }
    }

    public static void createTableUsers() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement statement = conn.createStatement();
            statement.execute(CREATE_TABLE_SQL);
        }
    }

    public static void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            if(conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
