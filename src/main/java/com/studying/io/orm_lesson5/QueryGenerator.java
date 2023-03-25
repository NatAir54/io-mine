package com.studying.io.orm_lesson5;

import java.io.Serializable;

public interface QueryGenerator {
    String findAll(Class<?> type);

    String findById(Class<?> type, Serializable id);

    String deleteById(Class<?> type, Serializable id);

    String insert(Object value);

    String update(Object value);
}
