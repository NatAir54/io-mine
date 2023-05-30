package com.studying.io.jdbc;

import java.sql.*;

public class Postgr2 {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static final String user = "postgres";
    private static final String password = "postgrsql098";
    private static final String url = "jdbc:postgresql://localhost:5432/main";

    public static void main(String[] args) throws Exception {
        try {
            connect();
            insertEx();
          //  clearTableEx();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    public static void rollBackEx() throws SQLException {
        statement.executeUpdate("INSERT INTO schoolpupils (name, score) VALUES ('Bob1', 80);");
        Savepoint sp1 = connection.setSavepoint();
        statement.executeUpdate("INSERT INTO schoolpupils (name, score) VALUES ('Bob2', 80);");
        connection.rollback(sp1);
        statement.executeUpdate("INSERT INTO schoolpupils (name, score) VALUES ('Bob3', 80);");
        connection.commit();
    }

    public static void batchEx() throws SQLException {
        connection.setAutoCommit(false);
        for (int i = 0; i < 10; i++) {
            preparedStatement.setString(1,"Bob" + (i + 1));
            preparedStatement.setInt(2, 50);
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        connection.commit();
    }

    public static void transactionAndPreparedStatement() throws SQLException {
        connection.setAutoCommit(false);
        for (int i = 0; i < 10000; i++) {
            preparedStatement.setString(1,"Bob" + (i + 1));
            preparedStatement.setInt(2, 50);
            preparedStatement.executeUpdate();
        }
        connection.commit();
    }

    public static void dropTableEx() throws SQLException {
        statement.executeUpdate("DROP TABLE schoolpupils;");
    }

    public static void clearTableEx() throws SQLException {
        statement.executeUpdate("DELETE FROM schoolpupils;");
    }

    public static void deleteEx() throws SQLException {
        statement.executeUpdate("DELETE FROM schoolpupils WHERE id = 3;");
    }

    public static void updateEx() throws SQLException {
        statement.executeUpdate("UPDATE schoolpupils SET score = 80 WHERE id = 1;");
    }

    private static void selectEx() {
        try (ResultSet rs = statement.executeQuery("SELECT * FROM schoolpupils;")) {
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " "
                        + rs.getString("name") + " " + rs.getInt("score"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertEx() throws SQLException {
        statement.executeUpdate("INSERT INTO schoolpupils (name, score) VALUES ('Bob3', 100);");
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
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("INSERT INTO schoolpupils (name, score) VALUES (?, ?);");
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Unable to connect");
        }
    }
}
