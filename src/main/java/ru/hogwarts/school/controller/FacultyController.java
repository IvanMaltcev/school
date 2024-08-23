package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.CrudeService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private final CrudeService<Faculty, String> facultyService;

    public FacultyController(CrudeService<Faculty, String> facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createdFaculty = facultyService.create(faculty);
        return ResponseEntity.ok(createdFaculty);
    }

    @GetMapping("{facultyId}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Long facultyId) {
        Faculty faculty = facultyService.getById(facultyId);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.update(faculty);
        if (updatedFaculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("{facultyId}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long facultyId) {
        facultyService.delete(facultyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getFacultyByColor(@RequestParam("color") String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(Collections.unmodifiableCollection(facultyService.getFilter(color)));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
}
