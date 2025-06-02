package com.ajaybhatia.skool.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ajaybhatia.skool.model.School;
import com.ajaybhatia.skool.repository.SchoolRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SchoolService {
  private final SchoolRepository schoolRepository;

  public School addSchool(School school) {
    return schoolRepository.save(school);
  }

  public List<School> getAllSchools() {
    return schoolRepository.findAll();
  }

  public School getSchoolById(UUID id) {
    return schoolRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("School not found with id: " + id));
  }
}
