package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService implements CrudeService<Faculty, String> {

    private final Map<Long, Faculty> faculties = new HashMap<>();
    private Long generatedFacultyId = 0L;

    @Override
    public Faculty create(Faculty faculty) {
        faculty.setId(++generatedFacultyId);
        faculties.put(generatedFacultyId, faculty);
        return faculty;
    }

    @Override
    public Faculty getById(Long facultyId) {
        return faculties.get(facultyId);
    }

    @Override
    public Faculty update(Faculty faculty) {
        if (!faculties.containsKey(faculty.getId())) {
            return null;
        }
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty delete(Long facultyId) {
        return faculties.remove(facultyId);
    }

    @Override
    public Collection<Faculty> getFilter(String color) {
        return faculties.values().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .toList();
    }
}
