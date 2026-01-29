package com.iamcarlchen.crm.customers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CustomerDtos {

  public record CustomerResponse(
      Long id,
      String name,
      String industry,
      String level,
      String phone,
      String email,
      String address,
      String owner,
      String createdAt,
      String updatedAt) {}

  public record CustomerUpsertRequest(
      @NotBlank String name,
      String industry,
      @NotBlank @Pattern(regexp = "[ABC]") String level,
      String phone,
      String email,
      String address,
      String owner) {}
}
