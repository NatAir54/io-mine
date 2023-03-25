package com.studying.io.orm_lesson5.entity;

import com.studying.io.orm_lesson5.annotation.Table;
import com.studying.io.orm_lesson5.annotation.Column;

@Table // name = "Person" optional parameter
public class Person {
    @Column
    private int id;
    @Column(name = "person_name")
    private String name;
    @Column(name = "person_salary")
    private double salary;
}
