package com.ajaybhatia.skool.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajaybhatia.skool.model.School;
import com.ajaybhatia.skool.service.SchoolService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/school")
@RestController
public class SchoolController {
  private final SchoolService schoolService;

  @PostMapping
  public ResponseEntity<School> addSchool(@RequestBody School school) {
    return ResponseEntity.ok(schoolService.addSchool(school));
  }

  @GetMapping
  public ResponseEntity<List<School>> getAllSchools() {
    return ResponseEntity.ok(schoolService.getAllSchools());
  }

  @GetMapping("/{id}")
  public ResponseEntity<School> getSchoolById(@PathVariable UUID id) {
    return ResponseEntity.ok(schoolService.getSchoolById(id));
  }
}
