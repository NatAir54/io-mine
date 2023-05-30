package com.studying.io.decoratorpattern;

public class MainTest {
    public static void main(String[] args) {
        UserDao userDao = ApplicationContext.getUserDao();
        userDao.saveUser("Natalie");
    }
}

class ApplicationContext {
    static UserDao getUserDao() {
        TransactionalUserDao transactionalUserDao = new TransactionalUserDao(new DbUserDao());
        return new LogInfoUserDao(transactionalUserDao );
    }
}

