package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FacultyServiceTest {

    private CrudeService<Faculty, String> out;

    private Faculty faculty1;
    private Faculty faculty2;
    private Faculty faculty3;
    private Faculty faculty4;


    @BeforeEach
    public void setUp() {

        out = new FacultyService();

        faculty1 = out.create(new Faculty(0L, "Blacks", "black"));
        faculty2 = out.create(new Faculty(0L, "Reds", "red"));
        faculty3 = out.create(new Faculty(0L, "Greys", "black"));
        faculty4 = out.create(new Faculty(0L, "Greens", "green"));

    }

    @Test
    public void createFacultyTesting() {

        Faculty expected = new Faculty(1L, "Blacks", "black");

        assertEquals(expected, faculty1);

    }

    @Test
    public void getFacultyById() {

        Faculty expected = new Faculty(3L, "Greys", "black");

        Faculty actual = out.getById(3L);

        assertEquals(expected, actual);
    }

    @Test
    public void updateFacultyTest() {

        Faculty expected = new Faculty(3L, "Greys", "grey");

        Faculty actual = out.update(new Faculty(3L, "Greys", "grey"));

        assertEquals(expected, actual);
    }

    @Test
    public void deleteFacultyTest() {

        Faculty expected = new Faculty(4L, "Greens", "green");

        Faculty actual = out.delete(4L);

        assertEquals(expected, actual);
    }

    @Test
    public void getFilter() {

        Collection<Faculty> expected = new ArrayList<>();

        expected.add(faculty1);
        expected.add(faculty3);

        Collection<Faculty> actual = out.getFilter("black");

        assertEquals(expected, actual);
    }
}
