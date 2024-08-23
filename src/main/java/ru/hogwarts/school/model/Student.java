package ru.hogwarts.school.model;

import lombok.Data;

@Data
public class Student {

    private Long id;
    private final String name;
    private final int age;

    public Student(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
