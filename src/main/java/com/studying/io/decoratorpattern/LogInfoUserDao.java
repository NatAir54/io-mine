package com.studying.io.decoratorpattern;

public class LogInfoUserDao implements UserDao {
    private UserDao target;

    public LogInfoUserDao(UserDao target) {
        this.target = target;
    }

    @Override
    public void saveUser(String user) {
        long start = System.currentTimeMillis();
        target.saveUser(user);
        long finish = System.currentTimeMillis();
        long time = finish - start;
        System.out.println("Save user: " + user + " took: " + time + "ms.");
    }
}
