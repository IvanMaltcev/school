package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentServiceTest {

    private CrudeService<Student, Integer> out;

    private Student student1;
    private Student student2;
    private Student student3;


    @BeforeEach
    public void setUp() {

        out = new StudentService();

        student1 = out.create(new Student(0L, "Mikel", 25));
        student2 = out.create(new Student(0L, "Jack", 25));
        student3 = out.create(new Student(0L, "Mary", 23));

    }

    @Test
    public void createStudentTesting() {

        Student expected = new Student(1L, "Mikel", 25);

        assertEquals(expected, student1);

    }

    @Test
    public void getStudentById() {

        Student expected = new Student(2L, "Jack", 25);

        Student actual = out.getById(2L);

        assertEquals(expected, actual);
    }

    @Test
    public void updateStudentTest() {

        Student expected = new Student(3L, "Mary", 24);

        Student actual = out.update(new Student(3L, "Mary", 24));

        assertEquals(expected, actual);
    }

    @Test
    public void deleteStudentTest() {

        Student expected = new Student(3L, "Mary", 23);

        Student actual = out.delete(3L);

        assertEquals(expected, actual);
    }

    @Test
    public void getFilter() {

        Collection<Student> expected = new ArrayList<>();

        expected.add(student1);
        expected.add(student2);

        Collection<Student> actual = out.getFilter(25);

        assertEquals(expected, actual);
    }

}
