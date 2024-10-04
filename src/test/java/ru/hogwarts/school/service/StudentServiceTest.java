package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.EmptyListException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepositoryMock;
    private StudentService out;

    private Student student1;
    private Student student2;
    private Student student3;


    @BeforeEach
    public void setUp() {

        out = new StudentServiceImp(studentRepositoryMock);

        student1 = new Student(1L, "Mikel", 25);
        student2 = new Student(2L, "Jack", 25);
        student3 = new Student(3L, "Ann", 23);

    }

    @Test
    public void createStudentTesting() {

        when(studentRepositoryMock.save(student1)).thenReturn(student1);

        Student expected = new Student(1L, "Mikel", 25);

        Student actual = out.create(student1);

        assertEquals(expected, actual);

    }

    @Test
    public void getStudentById() {

        when(studentRepositoryMock.findById(2L)).thenReturn(Optional.ofNullable(student2));

        Student expected = new Student(2L, "Jack", 25);

        Student actual = out.getById(2L);

        assertEquals(expected, actual);
    }

    @Test
    public void updateStudentTest() {

        Student updateStudent = new Student(3L, "Mary", 24);

        when(studentRepositoryMock.save(updateStudent)).thenReturn(updateStudent);

        Student expected = new Student(3L, "Mary", 24);

        Student actual = out.update(new Student(3L, "Mary", 24));

        assertEquals(expected, actual);
    }


    @Test
    public void getFilter() {

        when(studentRepositoryMock.findAll()).thenReturn(List.of(
                student1,
                student2,
                student3
        ));

        Collection<Student> expected = new ArrayList<>();

        expected.add(student1);
        expected.add(student2);

        Collection<Student> actual = out.getFilter(25);

        assertEquals(expected, actual);
    }

    @Test
    public void getListOfStudentNames() {

        when(studentRepositoryMock.findAll()).thenReturn(List.of(
                student1,
                student2,
                student3
        ));

        List<String> actual = out.getListOfStudentNames();

        List<String> expected = new ArrayList<>();
        expected.add(student3.getName());

        assertEquals(expected, actual);

    }


    @Test
    public void getAverageAgeAllStudents() {

        when(studentRepositoryMock.findAll()).thenReturn(List.of(
                student1,
                student2,
                student3
        ));

        double actual = out.getAverageAgeAllStudents();

        double expected = (double) (student1.getAge() + student2.getAge() + student3.getAge()) / 3;

        assertEquals(expected, actual);

    }

    @Test
    public void getAverageAgeAllStudentsWhenListStudentsEmpty() {

        when(studentRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(EmptyListException.class,
                () -> out.getAverageAgeAllStudents());

        assertEquals("List of students is empty!", exception.getMessage());

    }

}
