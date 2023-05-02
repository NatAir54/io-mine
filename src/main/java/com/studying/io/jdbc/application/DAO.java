package com.studying.io.jdbc.application;

public interface DAO<Entity, Key> {
    boolean create(Entity model);
    Entity read(Key key);
    boolean update(Entity model);
    boolean delete(Entity model);
}
