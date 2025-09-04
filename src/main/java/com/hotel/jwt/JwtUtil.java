package com.hotel.jwt;

import com.hotel.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "ed2a7c6542d9435a6986174135f36b919e3c31f5b9222ce4fd73696592f1cdata3804935d751a87f2845393b90fe8fac02c00aaa6dcf3ad2428f520098b2ae94fb89c1390541046df1695f2004c1d48229d4b7d5a590a8321987c136ce4e66c3d556f0920ac8c26bd90ecce726367f4ed5214ae9797034a586a46a8476b5e0139dac77a114241b91b6b95fa07cc13da7fad5865d256e53475aa3d345dcd768625c6f6be3ea64b95f67b7f2affected0bc58522999a7e98c9039cb766fe503b97b519758read45bf748a";
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 24 hour &  7 week

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // ✅ Generate Token
    public String generateToken(String username) {
        System.out.println("username" + username);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Generate token from User object
//    public String generateToken(User user) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("id", user.getId());
//        claims.put("email", user.getEmail());
//        claims.put("firstName", user.getFirstname());
//        claims.put("lastName", user.getLastname());
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(user.getId())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//    }

    // ✅ Extract Username
    public String extractUsername(String token) {
        return parseClaims(token).getBody().getSubject();
    }

    // ✅ Validate Token
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
