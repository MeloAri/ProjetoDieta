package com.ArielMelo.NutritionService.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET =
            "supersecretkeysupersecretkeysupersecretkeysupersecretkey";

    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hora

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 🔥 GERAR TOKEN
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔥 VALIDAR TOKEN
    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 🔥 EXTRAIR USER ID
    public String extractUserId(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 🔥 EXTRAIR CLAIMS
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
