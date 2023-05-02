package com.studying.io.jdbc.anno;

@Table(title = "cats")
public class Cat {
    @Column
    int id;

    @Column
    String name;

    @Column
    String color;

    @Column
    int weight;

    public Cat(int id, String name, String color, int weight) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.weight = weight;
    }
}
