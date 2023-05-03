package com.studying.io.jdbc.application;

import com.studying.io.jdbc.application.model.User;
import com.sun.istack.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class UserDAOTest {
    @NotNull
    private DAO<User, String> dao;

    @NotNull
    private Connection connection;

    @Before
    public void before() {
        try {
            String user = "postgres";
            String password = "1";
            String url = "jdbc:postgresql://localhost:5432/phones_magazine";
            connection = DriverManager.getConnection(url, user, password);
            dao = new UserDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void after() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void whenGetUserWhichExistThenReturnUser() {
        final User user = dao.read("admin");
        final User expected = new User();
        expected.setLogin("admin");
        expected.setPassword("123");
        expected.setRole(new User.Role(1, "admin"));
        assertThat(user, is(expected));
    }


    @Test
    public void whenUserIsNotExistThenReturnEmptyUserObj() {
        final User user = dao.read("xxx");
        assertThat(user.getId(), is(-1));
    }


    @Test
    public void whenAddUserWhichNotExistThenReturnUser() {
        final User user = new User(0, "test", "test", new User.Role(1, "admin"));
        final boolean result = dao.create(user);
        assertThat(result, is(true));
        //Clear test data.
        dao.delete(dao.read("test"));
    }


    @Test
    public void whenUserWhichExistDeletedThenReturnTrue() {
        final User user = new User(0, "test", "test", new User.Role(1, "admin"));
        dao.create(user);
        final User state = dao.read("test");
        boolean before = state.getId() != -1;
        user.setId(state.getId());

        final boolean after = dao.delete(user);
        assertTrue(before);
        assertTrue(after);
    }


    @Test
    public void whenUserNotExistThenReturnFalse() {
        assertFalse(dao.delete(new User(0, "test", "test", new User.Role(1, "admin"))));
    }


    @Test
    public void whenUpdateExistUserThenPasswordUpdated() {
        final User user = new User(0, "test", "test", new User.Role(1, "admin"));
        dao.create(user);
        final User gutted = dao.read("test");
        gutted.setPassword("updated");
        final boolean result = dao.update(gutted);
        final User updated = dao.read("test");
        assertThat(result, is(true));
        assertThat(updated.getPassword(), is("updated"));

        //Clear test data.
        dao.delete(updated);
    }


    @Test
    public void whenUpdateNotExistedUserThenPasswordUpdated() {
        assertThat(dao.update(new User()), is(false));
    }
}
