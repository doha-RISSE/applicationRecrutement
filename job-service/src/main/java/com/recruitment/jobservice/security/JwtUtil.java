package com.recruitment.jobservice.security;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final JwtDecoder jwtDecoder;

    public JwtUtil(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    public Long extractUserId(String token) {
        Jwt jwt = jwtDecoder.decode(token.replace("Bearer ", ""));
        return jwt.getClaim("userId");
    }

    public String extractRole(String token) {
        Jwt jwt = jwtDecoder.decode(token.replace("Bearer ", ""));
        return jwt.getClaim("role");
    }

    public String extractEmail(String token) {
        Jwt jwt = jwtDecoder.decode(token.replace("Bearer ", ""));
        return jwt.getSubject();
    }
}