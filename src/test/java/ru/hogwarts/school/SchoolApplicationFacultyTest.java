package ru.hogwarts.school;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SchoolApplicationFacultyTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private Faculty faculty;

    private Long facultyId;

    @BeforeEach
    void init() {
        faculty = new Faculty();
        faculty.setName("Math");
        faculty.setColor("black");
        facultyId = facultyController.createFaculty(faculty).getBody().getId();
        faculty.setId(facultyId);
    }

    @AfterEach
    void deleteObjectForTests() {
        facultyController.deleteFaculty(facultyId);
    }

    @Test
    public void createFacultyTesting() {
        ResponseEntity<Faculty> response = restTemplate.postForEntity("/faculties", faculty, Faculty.class);
        Long id = response.getBody().getId();
        Faculty expected = new Faculty(id, "Math", "black");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        facultyController.deleteFaculty(id);
    }

    @Test
    public void getFacultyByIdTesting() {
        faculty.setStudents(Collections.EMPTY_SET);
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculties/" + facultyId, Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(faculty, response.getBody());
    }

    @Test
    public void getFacultyByNotExistIdTesting() {
        ResponseEntity<Student> response = restTemplate.getForEntity("/faculties/50", Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void updateFacultyTesting() {
        faculty.setColor("brown");
        ResponseEntity<Faculty> response = restTemplate.exchange(
                "/faculties",
                HttpMethod.PUT,
                new HttpEntity<>(faculty),
                Faculty.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(faculty, response.getBody());
    }

    @Test
    public void updateNotExistFacultyTesting() {
        Faculty update = null;
        ResponseEntity<Faculty> response = restTemplate.exchange(
                "/faculties",
                HttpMethod.PUT,
                new HttpEntity<>(update),
                Faculty.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void deleteFacultyTesting() {
        ResponseEntity<Faculty> response = restTemplate.exchange("/faculties/" + facultyId,
                HttpMethod.DELETE,
                new HttpEntity<>(faculty),
                Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(facultyRepository.findAll().contains(faculty));
    }

    @Test
    public void getFacultyByColorTesting() {
        faculty.setStudents(Collections.EMPTY_SET);
        List<Faculty> expected = List.of(faculty);
        ResponseEntity<List<Faculty>> response = restTemplate.exchange(
                "/faculties?color=black",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    public void getFacultyByNameTesting() {
        faculty.setStudents(Collections.EMPTY_SET);
        List<Faculty> expected = List.of(faculty);
        ResponseEntity<List<Faculty>> response = restTemplate.exchange(
                "/faculties?name=Math",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    public void getListOfFacultyStudentsTesting() {
        ResponseEntity<Collection<Student>> response = restTemplate.exchange(
                "/faculties/students?facultyId=" + facultyId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
