package com.iamcarlchen.crm.auth;

import jakarta.validation.constraints.NotBlank;

public class AuthDtos {
  public record LoginRequest(@NotBlank String username, @NotBlank String password) {}

  public record LoginResponse(String token, String username, String role) {}

  public record MeResponse(Long id, String username, String name, String role) {}
}
