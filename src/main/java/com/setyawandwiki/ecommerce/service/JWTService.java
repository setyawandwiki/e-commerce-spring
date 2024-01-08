package com.setyawandwiki.ecommerce.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;

public interface JWTService {
  String generateToken(UserDetails userDetails);
  String extractUsername(String token);
  boolean isTokenValid(String token, UserDetails userDetails);
  String generateRefreshToken(HashMap<String, Object> extraClaims, UserDetails userDetails);
}
