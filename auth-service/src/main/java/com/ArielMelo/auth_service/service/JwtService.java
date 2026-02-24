package com.ArielMelo.auth_service.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final String SECRET =
            "supersecretkeysupersecretkeysupersecretkey";

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    private final long EXPIRATION = 1000 * 60 * 60 * 24; // 1 dia

    public String generateToken(String userId, String email, String role) {

        return Jwts.builder()
                .setSubject(userId)
                .claim("email", email)
                .claim("role", role) // 🔥 AGORA INCLUÍMOS ROLE
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    public String extractUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

