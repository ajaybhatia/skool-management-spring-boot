package com.ajaybhatia.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {
  private String id;
  private String name;
  private int age;
  private String gender;
  private School school;
}
