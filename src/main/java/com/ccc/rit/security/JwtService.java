package com.ccc.rit.security;

import com.ccc.rit.config.MessageProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.context.ApplicationScoped;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@ApplicationScoped
public class JwtService {

    private final Key signingKey;

    public JwtService() {
        String secret = MessageProvider.getSecurityProperty("security.token.secret");
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String username) {
        Instant now = Instant.now();
        Instant expiration = now.plus(30, ChronoUnit.MINUTES);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Optional<Claims> validateToken(String token) {
        try {
            return Optional.of(Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
