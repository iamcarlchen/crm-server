package com.iamcarlchen.crm.orders;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OrderDtos {
  public record OrderResponse(
      Long id,
      Long customerId,
      String customerName,
      String title,
      BigDecimal amount,
      String status,
      String createdAt) {}

  public record OrderUpsertRequest(
      @NotNull Long customerId,
      @NotBlank String title,
      @NotNull @DecimalMin("0.0") BigDecimal amount,
      @NotBlank String status) {}
}
