package com.ajaybhatia.student.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajaybhatia.student.model.Student;
import com.ajaybhatia.student.service.StudentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/api/student")
public class StudentController {
  private final StudentService studentService;

  @GetMapping
  public ResponseEntity<?> fetchStudents() {
    return studentService.fetchStudents();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> fetchStudentById(@PathVariable String id) {
    return studentService.fetchStudentById(id);
  }

  @PostMapping
  public ResponseEntity<?> createStudent(@RequestBody Student student) {
    return studentService.createStudent(student);
  }
}
