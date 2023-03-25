package com.studying.io.decoratorpattern;

public class TransactionalUserDao implements UserDao{
    private UserDao target;

    public TransactionalUserDao(UserDao target) {
        this.target = target;
    }

    @Override
    public void saveUser(String user) {
        System.out.println("Start transaction");
        target.saveUser(user);
        System.out.println("Commit transaction");
    }
}
