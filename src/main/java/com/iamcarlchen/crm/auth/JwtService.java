package com.iamcarlchen.crm.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final SecretKey key;
  private final int expireHours;

  public JwtService(
      @Value("${app.auth.jwtSecret}") String secret,
      @Value("${app.auth.jwtExpireHours:168}") int expireHours) {
    if (secret == null || secret.isBlank()) {
      throw new IllegalArgumentException(
          "app.auth.jwtSecret is required (set JWT_SECRET env var)");
    }

    // jjwt requires >= 256-bit key material for HS256/384/512.
    // We hash whatever secret string we get to 256 bits to ensure a valid HMAC key.
    byte[] keyBytes;
    try {
      keyBytes = MessageDigest.getInstance("SHA-256")
          .digest(secret.getBytes(StandardCharsets.UTF_8));
    } catch (Exception e) {
      throw new IllegalStateException("Failed to initialize JWT signing key", e);
    }

    this.key = Keys.hmacShaKeyFor(keyBytes);
    this.expireHours = expireHours;
  }

  public String issueToken(String subject, Map<String, Object> claims) {
    Instant now = Instant.now();
    Instant exp = now.plus(expireHours, ChronoUnit.HOURS);

    return Jwts.builder()
        .subject(subject)
        .issuedAt(Date.from(now))
        .expiration(Date.from(exp))
        .claims(claims)
        .signWith(key)
        .compact();
  }

  public JwtPayload verify(String token) {
    var jwt =
        Jwts.parser().verifyWith(key).build().parseSignedClaims(token);

    var claims = jwt.getPayload();
    return new JwtPayload(
        claims.getSubject(),
        claims.get("role", String.class),
        claims.getExpiration());
  }

  public record JwtPayload(String username, String role, Date exp) {}
}
