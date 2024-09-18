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
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SchoolApplicationStudentTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private Student student;
    private Long studentId;

    @BeforeEach
    void init() {
        student = new Student();
        student.setName("Tom");
        student.setAge(21);
        studentId = studentController.createStudent(student).getBody().getId();
        student.setId(studentId);
    }

    @AfterEach
    void deleteObjectForTests() {
        studentRepository.deleteById(studentId);
    }

    @Test
    public void createStudentTesting() {
        ResponseEntity<Student> response = restTemplate.postForEntity("/students", student, Student.class);
        Long id = response.getBody().getId();
        Student expected = new Student(id, "Tom", 21);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        studentRepository.deleteById(id);
    }

    @Test
    public void getStudentByIdTesting() {
        ResponseEntity<Student> response = restTemplate.getForEntity("/students/" + studentId, Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student, response.getBody());
    }

    @Test
    public void getStudentByNotExistIdTesting() {
        ResponseEntity<Student> response = restTemplate.getForEntity("/students/50", Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void updateStudentTesting() {
        student.setAge(20);
        ResponseEntity<Student> response = restTemplate.exchange(
                "/students",
                HttpMethod.PUT,
                new HttpEntity<>(student),
                Student.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student, response.getBody());
    }

    @Test
    public void updateNotExistStudentTesting() {
        Student update = null;
        ResponseEntity<Student> response = restTemplate.exchange(
                "/students",
                HttpMethod.PUT,
                new HttpEntity<>(update),
                Student.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void deleteStudentTesting() {
        ResponseEntity<Student> response = restTemplate.exchange("/students/" + studentId,
                HttpMethod.DELETE,
                new HttpEntity<>(student),
                Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(studentRepository.findAll().contains(student));
    }

    @Test
    public void getStudentsByAgeTesting() {
        List<Student> expected = List.of(student);
        ResponseEntity<List<Student>> response = restTemplate.exchange(
                "/students?age=21",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    public void getStudentsByAgeRangeTesting() {
        List<Student> expected = List.of(student);
        ResponseEntity<List<Student>> response = restTemplate.exchange(
                "/students?min=20&max=22",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    public void getStudentFacultyTesting() {
        ResponseEntity<Faculty> response = restTemplate
                .getForEntity("/students/faculty?studentId=" + studentId, Faculty.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
