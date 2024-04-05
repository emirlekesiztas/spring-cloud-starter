package com.emir.studentservice.service;

import com.emir.studentservice.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StudentService {

    public List<Student> studentList = new ArrayList<>();

    public Student addStudent(Student student) {
        studentList.add(student);
        return student;
    }

    public Student getStudent(Long id) {
        return studentList.stream().collect(Collectors.toMap(Student::id, Function.identity())).get(id);
    }

    public List<Student> findAll() {
        return studentList;
    }

    public List<Student> findByLessonId(Long lessonId) {
        return studentList.stream().filter(c -> c.lessonId().equals(lessonId)).collect(Collectors.toList());
    }
}
