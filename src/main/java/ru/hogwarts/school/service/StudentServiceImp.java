package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EmptyListException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class StudentServiceImp implements StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentServiceImp.class);

    @Autowired
    private final StudentRepository studentRepository;

    public StudentServiceImp(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    @Override
    public Student getById(Long studentId) {
        logger.info("Was invoked method for get student by id");
        Optional<Student> findStudent = studentRepository.findById(studentId);
        logger.debug("Find student: {}", findStudent);
        return findStudent.orElse(null);
    }

    @Override
    public Student update(Student student) {
        logger.info("Was invoked method for update student");
        Student updateStudent = studentRepository.save(student);
        logger.debug("Update student: {}", updateStudent);
        return updateStudent;
    }

    @Override
    public void delete(Long studentId) {
        logger.info("Was invoked method for delete student");
        studentRepository.deleteById(studentId);
    }

    @Override
    public Collection<Student> getFilter(Integer age) {
        logger.info("Was invoked method for get students by age");
        return studentRepository.findAll().stream()
                .filter(student -> Objects.equals(student.getAge(), age))
                .toList();
    }

    @Override
    public Collection<Student> findByParameters(Integer min, Integer max) {
        logger.info("Was invoked method for get students by age between {}, {}", min, max);
        return studentRepository.findStudentsByAgeBetween(min, max);
    }

    @Override
    public Faculty getStudentFaculty(Long studentId) {
        logger.info("Was invoked method for get student's faculty");
        Student findStudent = getById(studentId);
        if (findStudent == null) {
            logger.error("Find student: null");
            return null;
        }
        return findStudent.getFaculty();
    }

    @Override
    public Integer getAmountAllStudents() {
        logger.info("Was invoked method for get amount of all students");
        return studentRepository.getAmountAllStudents();
    }

    @Override
    public Double getAverageAgeStudents() {
        logger.info("Was invoked method for get average age of students");
        return studentRepository.getAverageAgeStudents();
    }

    @Override
    public Collection<Student> getLastFiveStudents() {
        logger.info("Was invoked method for get last five students");
        return studentRepository.getLastFiveStudents();
    }

    @Override
    public List<String> getListOfStudentNames() {
        logger.info("Was invoked method for get list of names students starting with the letter A");
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(s -> s.startsWith("A"))
                .toList();
    }

    @Override
    public Double getAverageAgeAllStudents() {
        logger.info("Was invoked method for get average age of all students");
        return studentRepository.findAll().stream()
                .map(Student::getAge)
                .mapToDouble(age -> age)
                .average()
                .orElseThrow(() -> new EmptyListException("List of students is empty!"));
    }

    @Override
    public void calculateValue() {
        long startTime1 = System.currentTimeMillis();
        int sum1 = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long workingTime1 = System.currentTimeMillis() - startTime1;
        logger.info("Working time method - {} \n Result: {}", workingTime1, sum1);

        long startTime2 = System.currentTimeMillis();
        int sum2 = IntStream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, Integer::sum);
        long workingTime2 = System.currentTimeMillis() - startTime2;
        logger.info("Working time method - {} \n Result: {}", workingTime2, sum2);

    }
}
