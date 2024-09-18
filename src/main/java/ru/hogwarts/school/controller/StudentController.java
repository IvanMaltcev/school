package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.create(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {
        Student student = studentService.getById(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.update(student);
        if (updatedStudent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("{studentId}")
    public void deleteStudent(@PathVariable Long studentId) {
        studentService.delete(studentId);
        ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam(required = false) Integer age,
                                                                @RequestParam(required = false) Integer min,
                                                                @RequestParam(required = false) Integer max) {
        if (!(age == null || age <= 0)) {
            return ResponseEntity.ok(Collections.unmodifiableCollection(studentService.getFilter(age)));
        }
        if (min > 0 && max > 0 && max > min) {
            return ResponseEntity.ok(Collections.unmodifiableCollection(
                    studentService.findByParameters(min, max)));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/faculty")
    public ResponseEntity<Faculty> getStudentFaculty(@RequestParam Long studentId) {
        Faculty studentFaculty = studentService.getStudentFaculty(studentId);
        if (studentFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentFaculty);
    }

}
