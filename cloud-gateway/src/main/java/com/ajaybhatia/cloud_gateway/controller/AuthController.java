package com.ajaybhatia.cloud_gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajaybhatia.cloud_gateway.dto.AuthenticationRequest;
import com.ajaybhatia.cloud_gateway.dto.AuthenticationResponse;
import com.ajaybhatia.cloud_gateway.model.User;
import com.ajaybhatia.cloud_gateway.service.JwtService;
import com.ajaybhatia.cloud_gateway.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final JwtService jwtService;
  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
      throws Exception {
    final String jwt = jwtService.createJwtToken(authenticationRequest);
    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody User user) {
    if (userService.findByUsername(user.getUsername()) != null) {
      return ResponseEntity.badRequest().body("Username is already taken.");
    }
    userService.save(user);
    return ResponseEntity.ok("User registered successfully.");
  }
}
