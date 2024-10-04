package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface FacultyService {
    Faculty create(Faculty faculty);

    Faculty getById(Long id);

    Faculty update(Faculty faculty);

    void delete(Long id);

    Collection<Faculty> findFacultyByParameter(String color, String name);

    Collection<Student> getListOfFacultyStudents(Long facultyId);

    String getLongestFacultyName();
}
