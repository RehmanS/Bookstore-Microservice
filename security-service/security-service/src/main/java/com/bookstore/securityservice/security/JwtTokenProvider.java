package com.bookstore.securityservice.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parser;

@Component
public class JwtTokenProvider {
    // @Value("${security-service.secret.key}")
    private final String SECRET = "dsbceuwhdikso3648gfhf73ekjd82jdkd042iudu743udjfir84u3h2634hgfjd86";
    private final long EXPIRES_IN = 604800000;


    // Userin dataları Authentication objectinin içində gəlir
    public String generateJwtToken(Authentication auth) {
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);

        return Jwts.builder()
                // setSubject(Long.toString(userDetails.getId()))  subject username də ola bilər.
                .subject(Long.toString(userDetails.getId()))
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(getSigningKey())
                .compact();
    }

    // Refresh Token
    public String generateJwtTokenByUserId(Long userId) {
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
        return Jwts.builder()
                .subject(Long.toString(userId))
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(getSigningKey())
                .compact();
    }

    Long getUserIdFromJwt(String token) {
        Claims claims = parser().decryptWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
        return Long.parseLong(claims.getSubject());
    }

    boolean validateToken(String token) {
        try {
            // parse etmək mümkünsə valid tokendir
            Jwts.parser().decryptWith(getSigningKey()).build().parseSignedClaims(token);
            return !isTokenExpired(token); // və vaxtı bitməyibsə
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiration = parser().decryptWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getExpiration();
        return expiration.before(new Date());
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = this.SECRET.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}