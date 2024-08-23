package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepositoryMock;
    private CrudeService<Faculty, String> out;

    private Faculty faculty1;
    private Faculty faculty2;
    private Faculty faculty3;
    private Faculty faculty4;


    @BeforeEach
    public void setUp() {

        out = new FacultyService(facultyRepositoryMock);

        faculty1 = new Faculty(1L, "Blacks", "black");
        faculty2 = new Faculty(2L, "Reds", "red");
        faculty3 = new Faculty(3L, "Greys", "black");
        faculty4 = new Faculty(4L, "Greens", "green");


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
    public void getFilter() {

        when(facultyRepositoryMock.findAll()).thenReturn(List.of(
                faculty1,
                faculty2,
                faculty3,
                faculty4
        ));

        Collection<Faculty> expected = new ArrayList<>();

        expected.add(faculty1);
        expected.add(faculty3);

        Collection<Faculty> actual = out.getFilter("black");

        assertEquals(expected, actual);
    }
}
