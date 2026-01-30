package com.iamcarlchen.crm.auth;

import static org.assertj.core.api.Assertions.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.junit.jupiter.api.Test;

public class JwtServiceTest {

  @Test
  void issueToken_and_verify_roundTrip() {
    JwtService jwt = new JwtService("test-secret", 1);

    String token = jwt.issueToken("alice", java.util.Map.of("role", "ADMIN"));
    JwtService.JwtPayload payload = jwt.verify(token);

    assertThat(payload.username()).isEqualTo("alice");
    assertThat(payload.role()).isEqualTo("ADMIN");
    assertThat(payload.exp()).isNotNull();
  }

  @Test
  void constructor_hashes_secret_to_256bit_hmac_key() throws Exception {
    // This is mainly a regression check: jjwt requires >= 256-bit key material.
    JwtService jwt = new JwtService("short", 1);
    String token = jwt.issueToken("bob", java.util.Map.of("role", "USER"));

    // Verify using the same hashing approach so we ensure the token is HS-signed correctly.
    byte[] keyBytes = MessageDigest.getInstance("SHA-256")
        .digest("short".getBytes(StandardCharsets.UTF_8));

    Claims claims = Jwts.parser()
        .verifyWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes))
        .build()
        .parseSignedClaims(token)
        .getPayload();

    assertThat(claims.getSubject()).isEqualTo("bob");
    assertThat(claims.get("role", String.class)).isEqualTo("USER");
  }
}
