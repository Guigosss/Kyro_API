package com.bulk.kyro.api.utils;

import com.bulk.kyro.dl.entities.UserEntity;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    private final JwtBuilder jwtBuilder;
    private final JwtParser jwtParser;

    public JwtUtils() {
        String jwtSecret = "8fK2mP9xQ7vL4nR6tY3wA1sD5gH8jZ0cX2bN6mV9pQ4rT7uK"; //- Temporary hardcoded
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        jwtBuilder = Jwts.builder().signWith(secretKey);
        jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }

    public String generateToken(UserEntity user) {

        return jwtBuilder
                .subject(user.getUsername())
                .claim("id", user.getId())
                .claim("role", user.getRole().getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .compact();
    }

    public Claims parseToken(String token) {
        return jwtParser.parseSignedClaims(token).getPayload();
    }

    public String getUsername(String token) {
        return parseToken(token).getSubject();
    }

    public Integer getId(String token) {
        return parseToken(token).get("id", Integer.class);
    }

    public String getRole(String token) {
        return parseToken(token).get("role", String.class);
    }

    public boolean validateToken(String token) {
        Claims claims = parseToken(token);

        Date now = new Date();

        return now.after(claims.getIssuedAt()) && now.before(claims.getExpiration());
    }
}
