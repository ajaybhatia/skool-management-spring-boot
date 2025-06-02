package com.ajaybhatia.cloud_gateway.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {
  private String username;
  private String password;
}
