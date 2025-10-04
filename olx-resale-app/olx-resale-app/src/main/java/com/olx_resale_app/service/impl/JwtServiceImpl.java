package com.olx_resale_app.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import com.olx_resale_app.entity.AppUser;
import com.olx_resale_app.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${spring.secret-key}")
    private String secretKey;

    @Value("${spring.expiryDuration}")
    private int expiryDuration;

    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateToken(AppUser user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("name",user.getName())
                .claim("email", user.getEmail())
                .claim("roles", user.getRoles())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiryDuration))
                .signWith(getSecretKey())
                .compact();
    }

    @Override
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String subject = claims.getSubject();
        return Long.valueOf(subject);
    }
}
