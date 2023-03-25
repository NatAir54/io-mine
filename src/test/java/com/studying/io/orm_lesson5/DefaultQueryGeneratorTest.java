package com.studying.io.orm_lesson5;

import com.studying.io.orm_lesson5.entity.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultQueryGeneratorTest {
    @Test
    public void testGenerateSelectAll() {
        String expectedQuery = "SELECT id, person_name, person_salary FROM Person;";
        QueryGenerator queryGenerator = new DefaultQueryGenerator();
        String selectAllQuery = queryGenerator.findAll(Person.class);
        assertEquals(expectedQuery, selectAllQuery);
    }
}
