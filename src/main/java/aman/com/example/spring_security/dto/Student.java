package aman.com.example.spring_security.dto;

import lombok.Data;

@Data
public class Student {

    private int studentId;
    private String studentName;


    public Student(int studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }
}
