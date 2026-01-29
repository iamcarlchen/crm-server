package com.iamcarlchen.crm.visits;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class VisitDtos {
  public record VisitResponse(
      Long id,
      Long customerId,
      String customerName,
      LocalDate date,
      String method,
      String summary,
      String nextAction,
      String owner) {}

  public record VisitUpsertRequest(
      @NotNull Long customerId,
      @NotNull LocalDate date,
      @NotBlank String method,
      @NotBlank String summary,
      String nextAction,
      String owner) {}
}
