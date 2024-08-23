package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService implements CrudeService<Student, Integer> {

    private final Map<Long, Student> students = new HashMap<>();
    private Long generatedStudentId = 0L;

    @Override
    public Student create(Student student) {
        student.setId(++generatedStudentId);
        students.put(generatedStudentId, student);
        return student;
    }

    @Override
    public Student getById(Long studentId) {
        return students.get(studentId);
    }

    @Override
    public Student update(Student student) {
        if (!students.containsKey(student.getId())) {
            return null;
        }
        students.put(student.getId(), student);
        return student;
    }

    @Override
    public Student delete(Long studentId) {
        return students.remove(studentId);
    }

    @Override
    public Collection<Student> getFilter(Integer age) {
        return students.values().stream()
                .filter(student -> student.getAge() == age)
                .toList();
    }
}
