package com.kiet.student.controller;

import com.kiet.student.model.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final List<Student> students = new ArrayList<>();

    public StudentController() {
        students.add(new Student(101, "Rahul Sharma",
                "rahul@kiet.edu", "CSE", 2, 8.65));

        students.add(new Student(102, "Priya Verma",
                "priya@kiet.edu", "CSE", 2, 9.20));

        students.add(new Student(103, "Aman Singh",
                "aman@kiet.edu", "IT", 3, 8.40));

        students.add(new Student(104, "Neha Gupta",
                "neha@kiet.edu", "ECE", 2, 9.10));

        students.add(new Student(105, "Arjun Kumar",
                "arjun@kiet.edu", "CSE", 3, 7.90));
    }

    // Q3: Get all students
    @GetMapping
    public List<Student> getAllStudents() {
        return students;
    }

    // Q3: Get student by ID
    @GetMapping("/{studentId}")
    public Object getStudentById(@PathVariable int studentId) {
        for (Student s : students) {
            if (s.getStudentId() == studentId) {
                return s;
            }
        }

        return Map.of("message", "Student not found");
    }

    // Q4: Add student
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        students.add(student);
        return student;
    }

    // Q5: Update student
    @PutMapping("/{studentId}")
    public Map<String, String> updateStudent(
            @PathVariable int studentId,
            @RequestBody Student updatedStudent) {

        for (Student s : students) {
            if (s.getStudentId() == studentId) {
                s.setName(updatedStudent.getName());
                s.setEmail(updatedStudent.getEmail());
                s.setBranch(updatedStudent.getBranch());
                s.setYear(updatedStudent.getYear());
                s.setCgpa(updatedStudent.getCgpa());

                return Map.of(
                        "message", "Student updated successfully");
            }
        }

        return Map.of("message", "Student not found");
    }

    // Q5: Delete student
    @DeleteMapping("/{studentId}")
    public Map<String, String> deleteStudent(
            @PathVariable int studentId) {

        boolean removed = students.removeIf(
                s -> s.getStudentId() == studentId);

        if (removed) {
            return Map.of(
                    "message", "Student deleted successfully");
        }

        return Map.of("message", "Student not found");
    }

    // Q6: Search by branch and year
    @GetMapping("/search")
    public List<Student> searchStudents(
            @RequestParam String branch,
            @RequestParam int year) {

        List<Student> result = new ArrayList<>();

        for (Student s : students) {
            if (s.getBranch().equalsIgnoreCase(branch)
                    && s.getYear() == year) {
                result.add(s);
            }
        }

        return result;
    }

    // Q6: Find students by minimum CGPA
    @GetMapping("/topper")
    public List<Student> getToppers(
            @RequestParam double cgpa) {

        List<Student> result = new ArrayList<>();

        for (Student s : students) {
            if (s.getCgpa() >= cgpa) {
                result.add(s);
            }
        }

        return result;
    }
}

