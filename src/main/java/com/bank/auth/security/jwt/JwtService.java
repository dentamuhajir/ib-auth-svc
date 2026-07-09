package com.bank.auth.security.jwt;

import com.bank.auth.security.userdetails.AppUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${spring.jwt.secret-key}")
    private String secret;

    @Value("${spring.jwt.ttl-ms}")
    private long ttl;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UserDetails userDetails) {

        AppUserDetails user = (AppUserDetails) userDetails;

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("user_id", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ttl))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return parse(token).getSubject();
    }

    public String extractUserId(String token) {
        return parse(token).get("user_id", String.class);
    }

    private Claims parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validate(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername());
    }
}