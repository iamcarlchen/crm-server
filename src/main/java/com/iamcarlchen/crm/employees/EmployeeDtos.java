package com.iamcarlchen.crm.employees;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmployeeDtos {

  public record EmployeeResponse(
      Long id,
      String username,
      String name,
      String phone,
      String email,
      String role,
      String status,
      String createdAt,
      String updatedAt) {}

  public record EmployeeUpsertRequest(
      @NotBlank String username,
      String password,
      @NotBlank String name,
      String phone,
      @Email String email,
      String role,
      String status) {}
}
