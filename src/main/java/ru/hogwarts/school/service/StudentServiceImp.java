package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentServiceImp implements StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    public StudentServiceImp(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getById(Long studentId) {
        Optional<Student> findStudent = studentRepository.findById(studentId);
        return findStudent.orElse(null);
    }

    @Override
    public Student update(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void delete(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    @Override
    public Collection<Student> getFilter(Integer age) {
        return studentRepository.findAll().stream()
                .filter(student -> student.getAge() == age)
                .toList();
    }

    @Override
    public Collection<Student> findByParameters(Integer min, Integer max) {
        return studentRepository.findStudentsByAgeBetween(min, max);
    }

    @Override
    public Faculty getStudentFaculty(Long studentId) {
        if (getById(studentId) == null) {
            return null;
        }
        return getById(studentId).getFaculty();
    }

    @Override
    public Integer getAmountAllStudents() {
        return studentRepository.getAmountAllStudents();
    }

    @Override
    public Double getAverageAgeStudents() {
        return studentRepository.getAverageAgeStudents();
    }

    @Override
    public Collection<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }

}
