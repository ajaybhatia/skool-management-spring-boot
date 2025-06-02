package com.ajaybhatia.cloud_gateway.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
  @Value("${jwt.secret}")
  private String secret;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private SecretKey getSignKey() {
    try {
      // Try to decode as Base64 first
      byte[] keyBytes = Decoders.BASE64.decode(secret);
      return Keys.hmacShaKeyFor(keyBytes);
    } catch (Exception e) {
      // If Base64 decoding fails, use the secret as plain text
      byte[] keyBytes = secret.getBytes();
      // Ensure the key is at least 256 bits (32 bytes) for HS256
      if (keyBytes.length < 32) {
        byte[] paddedKey = new byte[32];
        System.arraycopy(keyBytes, 0, paddedKey, 0, Math.min(keyBytes.length, 32));
        keyBytes = paddedKey;
      }
      return Keys.hmacShaKeyFor(keyBytes);
    }
  }

  public Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(getSignKey()).build()
        .parseSignedClaims(token)
        .getPayload();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userDetails.getUsername());
  }

  private String createToken(Map<String, Object> claims, String subject) {
    claims.put(Claims.SUBJECT, subject);
    return Jwts.builder().claims(claims)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
        .signWith(getSignKey()).compact();
  }
}
