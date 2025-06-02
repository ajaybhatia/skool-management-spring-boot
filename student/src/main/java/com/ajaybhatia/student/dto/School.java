package com.ajaybhatia.student.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class School {
  private UUID id;
  private String name;
  private String address;
  private String principalName;
}
