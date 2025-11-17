package com.hotelgo.util;

import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "secret_key_hotel";
    private static final long EXPIRATION_TIME = 3600000; // 1 jam

    public static String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static Jws<Claims> validateToken(String token) throws JwtException {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
    }

    public static String getRole(String token) {
        return validateToken(token).getBody().get("role", String.class);
    }

    public static String getUsername(String token) {
        return validateToken(token).getBody().getSubject();
    }
}
