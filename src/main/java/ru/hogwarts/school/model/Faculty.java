package ru.hogwarts.school.model;

import lombok.Data;

@Data
public class Faculty {

    private Long id;
    private final String name;
    private final String color;

    public Faculty(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }
}
