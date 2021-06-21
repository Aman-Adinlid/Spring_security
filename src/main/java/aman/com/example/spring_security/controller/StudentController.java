package aman.com.example.spring_security.controller;

import aman.com.example.spring_security.dto.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("api/v1/student")
public class StudentController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Aman A"),
            new Student(2, "Anna Sam"),
            new Student(3, "Sam Smith")
    );

    @GetMapping("{studentId}")
    public Student getStudents(@PathVariable("studentId") Integer studentId) {
        return STUDENTS.stream().filter(student -> studentId.
                equals(student.getStudentId())).findFirst().
                orElseThrow(() -> new IllegalArgumentException("student " + studentId + "does not exist"));

    }
}
