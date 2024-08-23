package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.CrudeService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("students")
public class StudentController {

    private final CrudeService<Student, Integer> studentService;

    public StudentController(CrudeService<Student, Integer> studentService) {
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
    public ResponseEntity<Student> deleteStudent(@PathVariable Long studentId) {
        studentService.delete(studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam("age") int age) {
        if (age > 0) {
            return ResponseEntity.ok(Collections.unmodifiableCollection(studentService.getFilter(age)));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
}
