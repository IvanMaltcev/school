package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EmptyListException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

@Service
public class FacultyServiceImp implements FacultyService {

    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImp.class);

    @Autowired
    private final FacultyRepository facultyRepository;

    public FacultyServiceImp(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty create(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getById(Long facultyId) {
        logger.info("Was invoked method for get faculty by id");
        Optional<Faculty> findFaculty = facultyRepository.findById(facultyId);
        logger.debug("Find faculty: {}", findFaculty);
        return findFaculty.orElse(null);
    }

    @Override
    public Faculty update(Faculty faculty) {
        logger.info("Was invoked method for update faculty");
        Faculty updateFaculty = facultyRepository.save(faculty);
        logger.debug("Update faculty: {}", updateFaculty);
        return updateFaculty;
    }

    @Override
    public void delete(Long facultyId) {
        logger.info("Was invoked method for delete faculty");
        facultyRepository.deleteById(facultyId);
    }

    @Override
    public Collection<Faculty> findFacultyByParameter(String color, String name) {
        logger.info("Was invoked method for find faculty by color or name {}, {}", color, name);
        return facultyRepository.findFacultyByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

    @Override
    public Collection<Student> getListOfFacultyStudents(Long facultyId) {
        logger.info("Was invoked method for get a list of faculty students");
        if (getById(facultyId) == null) {
            logger.error("Find faculty: null");
            return Collections.emptyList();
        }
        return getById(facultyId).getStudents();
    }

    @Override
    public String getLongestFacultyName() {
        logger.info("Was invoked method for get the longest faculty name");
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length))
                .orElseThrow(() -> new EmptyListException("List of faculties is empty!"));
    }
}
