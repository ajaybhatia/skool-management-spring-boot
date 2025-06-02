package com.ajaybhatia.student.service;

import java.util.List;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ajaybhatia.student.dto.School;
import com.ajaybhatia.student.dto.StudentResponse;
import com.ajaybhatia.student.model.Student;
import com.ajaybhatia.student.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentService {
  private final StudentRepository studentRepository;
  private final RestTemplate restTemplate;

  public ResponseEntity<?> createStudent(Student student) {
    try {
      School school = restTemplate.getForObject("http://SCHOOL-SERVICE/api/school/" + student.getSchoolId(),
          School.class);
      if (school == null) {
        return new ResponseEntity<>("School not found", HttpStatus.NOT_FOUND);
      }
      Student savedStudent = studentRepository.save(student);
      StudentResponse studentResponse = StudentResponse.builder()
          .id(savedStudent.getId())
          .name(savedStudent.getName())
          .age(savedStudent.getAge())
          .gender(savedStudent.getGender())
          .school(school)
          .build();
      return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<?> fetchStudentById(String id) {
    Optional<Student> student = studentRepository.findById(id);
    if (student.isPresent()) {
      School school = restTemplate.getForObject("http://SCHOOL-SERVICE/api/school/" + student.get().getSchoolId(),
          School.class);
      StudentResponse studentResponse = StudentResponse.builder()
          .id(student.get().getId())
          .name(student.get().getName())
          .age(student.get().getAge())
          .gender(student.get().getGender())
          .school(school)
          .build();
      return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
    }
  }

  public ResponseEntity<?> fetchStudents() {
    List<Student> students = studentRepository.findAll();
    if (students.isEmpty()) {
      return new ResponseEntity<>("No students found", HttpStatus.NOT_FOUND);
    } else {
      List<School> schools = restTemplate.exchange(
          "http://SCHOOL-SERVICE/api/school",
          org.springframework.http.HttpMethod.GET,
          null,
          new ParameterizedTypeReference<List<School>>() {
          }).getBody();
      List<StudentResponse> studentResponses = students.stream().map(student -> {
        School school = schools.stream()
            .filter(s -> s.getId().equals(student.getSchoolId()))
            .findFirst()
            .orElse(null);
        return StudentResponse.builder()
            .id(student.getId())
            .name(student.getName())
            .age(student.getAge())
            .gender(student.getGender())
            .school(school)
            .build();
      }).toList();
      return new ResponseEntity<>(studentResponses, HttpStatus.OK);
    }
  }
}
