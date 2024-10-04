package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.EmptyListException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepositoryMock;
    private FacultyService out;

    private Faculty faculty1;
    private Faculty faculty2;
    private Faculty faculty3;


    @BeforeEach
    public void setUp() {

        out = new FacultyServiceImp(facultyRepositoryMock);

        faculty1 = new Faculty(1L, "Blacks", "black");
        faculty2 = new Faculty(2L, "Reds", "red");
        faculty3 = new Faculty(3L, "Greys", "black");


    }

    @Test
    public void createFacultyTesting() {

        when(facultyRepositoryMock.save(faculty1)).thenReturn(faculty1);

        Faculty expected = new Faculty(1L, "Blacks", "black");

        Faculty actual = out.create(faculty1);

        assertEquals(expected, actual);

    }

    @Test
    public void getFacultyById() {

        when(facultyRepositoryMock.findById(3L)).thenReturn(Optional.ofNullable(faculty3));

        Faculty expected = new Faculty(3L, "Greys", "black");

        Faculty actual = out.getById(3L);

        assertEquals(expected, actual);
    }

    @Test
    public void updateFacultyTest() {

        Faculty updateFaculty = new Faculty(3L, "Greys", "grey");

        when(facultyRepositoryMock.save(updateFaculty)).thenReturn(updateFaculty);

        Faculty expected = new Faculty(3L, "Greys", "grey");

        Faculty actual = out.update(new Faculty(3L, "Greys", "grey"));

        assertEquals(expected, actual);
    }

    @Test
    public void findFacultyByName() {

        when(facultyRepositoryMock.findFacultyByColorIgnoreCaseOrNameIgnoreCase(null, "Reds"))
                .thenReturn(List.of(
                        faculty2
                ));

        Collection<Faculty> expected = new ArrayList<>();

        expected.add(faculty2);

        Collection<Faculty> actual = out.findFacultyByParameter(null, "Reds");

        assertEquals(expected, actual);
    }

    @Test
    public void findFacultyByColor() {

        when(facultyRepositoryMock.findFacultyByColorIgnoreCaseOrNameIgnoreCase("black", null))
                .thenReturn(List.of(
                        faculty1,
                        faculty3
                ));

        Collection<Faculty> expected = new ArrayList<>();

        expected.add(faculty1);
        expected.add(faculty3);

        Collection<Faculty> actual = out.findFacultyByParameter("black", null);

        assertEquals(expected, actual);
    }

    @Test
    public void getLongestFacultyNameTest() {

        when(facultyRepositoryMock.findAll()).thenReturn(List.of(
                faculty1,
                faculty2,
                faculty3
        ));

        String actual = out.getLongestFacultyName();
        assertEquals("Blacks", actual);
    }

    @Test
    public void getLongestFacultyNameWhenListFacultiesEmpty() {

        when(facultyRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(EmptyListException.class,
                () -> out.getLongestFacultyName());

        assertEquals("List of faculties is empty!", exception.getMessage());

    }
}
