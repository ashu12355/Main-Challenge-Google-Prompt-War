package com.wellbeing.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Utility class for JWT generation, validation, and parsing.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generates a JWT token for the authenticated username.
     *
     * @param username the username
     * @return the generated JWT token string
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extracts the username from a JWT token.
     *
     * @param token the JWT token
     * @return the username claim
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    /**
     * Validates a JWT token signature and expiry.
     *
     * @param token the JWT token
     * @return true if valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            try {
                java.nio.file.Files.writeString(
                        java.nio.file.Path.of("/Users/ashutoshmain/Ashutosh/MainChallangePromptWar/jwt_diagnostics.log"),
                        "[JWT Util] Validation Exception: " + e.toString() + "\n",
                        java.nio.file.StandardOpenOption.CREATE,
                        java.nio.file.StandardOpenOption.APPEND
                );
            } catch (Exception ignored) {}
            return false;
        }
    }
}
