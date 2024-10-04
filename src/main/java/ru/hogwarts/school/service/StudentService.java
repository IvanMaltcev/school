package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {

    Student create(Student t);

    Student getById(Long id);

    Student update(Student t);

    void delete(Long id);

    Collection<Student> getFilter(Integer age);

    Collection<Student> findByParameters(Integer min, Integer max);

    Faculty getStudentFaculty(Long studentId);

    Integer getAmountAllStudents();

    Double getAverageAgeStudents();

    Collection<Student> getLastFiveStudents();

    List<String> getListOfStudentNames();

    Double getAverageAgeAllStudents();

    void calculateValue();
}
