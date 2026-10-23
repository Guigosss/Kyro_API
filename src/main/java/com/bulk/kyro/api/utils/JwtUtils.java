package com.bulk.kyro.api.utils;

import com.bulk.kyro.api.models.user.UserContext;
import com.bulk.kyro.dl.entities.UserEntity;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    private final JwtBuilder jwtBuilder;
    private final JwtParser jwtParser;

    private final long accessTokenValidity; //- 15 minutes
    private final long refreshTokenValidity; //- 7 days

    public JwtUtils(
            @Value("${jwt.secret}") String jwtSecret,
            @Value("${jwt.accessTokenValidity}") long accessTokenValidity,
            @Value("${jwt.refreshTokenValidity}") long refreshTokenValidity
    ) {
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;

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
                .expiration(new Date(System.currentTimeMillis() + accessTokenValidity * 1000))
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

    public UserContext getUser(String token) {
        return new UserContext(getId(token), getUsername(token), getRole(token));
    }

    public boolean validateToken(String token) {
        Claims claims = parseToken(token);
        Date now = new Date();
        return now.after(claims.getIssuedAt()) && now.before(claims.getExpiration());
    }

    public String generateRefreshToken(UserEntity user) {
        return jwtBuilder
                .subject(user.getUsername())
                .claim("id", user.getId())
                .claim("role", user.getRole().getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenValidity * 1000))
                .compact();
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token);
    }
}
