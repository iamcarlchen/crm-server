package com.iamcarlchen.crm.finance;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class FinanceRecordDtos {
  public record FinanceRecordResponse(
      Long id,
      Long customerId,
      String customerName,
      String type,
      BigDecimal amount,
      LocalDate date,
      String status,
      String note) {}

  public record FinanceRecordUpsertRequest(
      @NotNull Long customerId,
      @NotBlank String type,
      @NotNull @DecimalMin("0.0") BigDecimal amount,
      @NotNull LocalDate date,
      @NotBlank String status,
      String note) {}
}
