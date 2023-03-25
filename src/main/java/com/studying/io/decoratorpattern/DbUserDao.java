package com.studying.io.decoratorpattern;

// suffix Dao - for classes which are responsible for communication with the database
public class DbUserDao implements UserDao {
    @Override
    public void saveUser(String user) {
        System.out.println("[start] Save user to db");
        try {
            Thread.sleep((long)Math.random() * 5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("[finish] Save user to db");
    }
}
