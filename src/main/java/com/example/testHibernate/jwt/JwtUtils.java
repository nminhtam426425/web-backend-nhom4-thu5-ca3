package com.example.testHibernate.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtils {

    private static final String SECRET = "mysecretkeymysecretkeymysecretkey123";
    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    private static final long ACCESS_EXP = 1000 * 60 * 15; // 15 phút
    private static final long REFRESH_EXP = 1000 * 60 * 60 * 24; // 1 ngày
    public static String generateToken(String userId, String role) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXP))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public static String generateRefreshToken(String userId, String role) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXP))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static boolean validateToken(String token) {
        try {
            getAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}