package com.ajaybhatia.student.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ajaybhatia.student.model.Student;

public interface StudentRepository extends MongoRepository<Student, String> {
}
