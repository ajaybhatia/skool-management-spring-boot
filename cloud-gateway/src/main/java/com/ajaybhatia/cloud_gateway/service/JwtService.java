package com.ajaybhatia.cloud_gateway.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ajaybhatia.cloud_gateway.dto.AuthenticationRequest;
import com.ajaybhatia.cloud_gateway.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtService {
  private final AuthenticationManager authenticationManager;
  private final CustomUserDetailsService userDetailsService;
  private final JwtUtil jwtUtil;

  public String createJwtToken(AuthenticationRequest authenticationRequest) throws Exception {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        authenticationRequest.getUsername(), authenticationRequest.getPassword()));

    final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    return jwtUtil.generateToken(userDetails);
  }
}
