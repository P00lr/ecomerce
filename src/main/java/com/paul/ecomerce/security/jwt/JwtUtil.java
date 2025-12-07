package com.paul.ecomerce.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.paul.ecomerce.exception.custom.JwtValidationException;
import com.paul.ecomerce.model.entity.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

    private final Key secretKey;
    private final long expirationMs;
    private static final long REFRESH_EXPIRATION_MS = 604800000; // 7 días

    public JwtUtil(
            @Value("${spring.security.jwt.secret}") String secrectKey,
            @Value("${spring.security.jwt.expiration}") long expirationMs) {

        this.secretKey = Keys.hmacShaKeyFor(secrectKey.getBytes());
        this.expirationMs = expirationMs;
    }

    public String generateToken(Long userId, String username, List<Role> roles) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date(now))
            .setNotBefore(new Date(now))
            .setExpiration(new Date(now + expirationMs))
            .claim("uid", userId)
            .claim("roles", roles.stream().map(Role::getName).toList())
            .claim("type", "access")
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    public String generateRefreshToken(Long userId, String username) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + REFRESH_EXPIRATION_MS))
            .claim("uid", userId)
            .claim("type", "refresh")
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public Long getUserIdFromToken(String token) {
        return Long.valueOf(getClaims(token).get("uid").toString());
    }

    public List<String> getRolesFromToken(String token) {
        Object roles = getClaims(token).get("roles");
        if (roles instanceof List<?> list)
            return list.stream().map(Object::toString).toList();
        return List.of();
    }

    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtValidationException e) {
            return false;
        }
    }

    public boolean isAccessToken(String token) {
        try {
            String type = getClaims(token).get("type", String.class);
            return "access".equals(type);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRefreshToken(String token) {
        try {
            String type = getClaims(token).get("type", String.class);
            return "refresh".equals(type);
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtValidationException("token_expired", e);
        } catch (JwtException e) {
            throw new JwtValidationException("token_invalid", e);
        }
    }
}

