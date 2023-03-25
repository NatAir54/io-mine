package com.studying.io.orm;

import com.studying.io.orm.annotation.Column;
import com.studying.io.orm.annotation.Table;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.StringJoiner;

public class DefaultQueryGenerator implements QueryGenerator {
    @Override
    public String findAll(Class<?> clazz) {
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        if (tableAnnotation == null) {
            throw new IllegalArgumentException("Class is not ORM entity");
        }
        String tableName = tableAnnotation.name().isEmpty() ? clazz.getSimpleName() : tableAnnotation.name();

        StringBuilder query = new StringBuilder("SELECT ");
        StringJoiner columnNames = new StringJoiner(", ");

        for (Field declaredField : clazz.getDeclaredFields()) {
            Column columnAnnotation = declaredField.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                String columnName = columnAnnotation.name().isEmpty() ? declaredField.getName() : columnAnnotation.name();
                columnNames.add(columnName);
            }
        }
        query.append(columnNames);
        query.append(" FROM ");
        query.append(tableName);

        return query + ";";
    }

    @Override
    public String findById(Class<?> clazz, Serializable id) {
        return null;
    }

    @Override
    public String deleteById(Class<?> type, Serializable id) {
        return null;
    }

    @Override
    public String insert(Object value) {
        return null;
    }

    @Override
    public String update(Object value) {
        return null;
    }
}
