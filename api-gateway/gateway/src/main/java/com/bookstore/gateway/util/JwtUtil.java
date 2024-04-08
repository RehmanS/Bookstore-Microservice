package com.bookstore.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtUtil {
    public final String SECRET = "dsbceuwhdikso3648gfhf73ekjd82jdkd042iudu743udjfir84u3h2634hgfjd86";
    Logger logger = LoggerFactory.getLogger(this.getClass());
    public boolean validateToken(final String token) {
        try {
            final Jws<Claims> claimsJws = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = this.SECRET.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}