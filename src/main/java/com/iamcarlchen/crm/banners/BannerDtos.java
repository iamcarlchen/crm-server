package com.iamcarlchen.crm.banners;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class BannerDtos {

  public record BannerUpsertRequest(
      @NotBlank String name,
      @NotBlank String position,
      @NotBlank String status,
      String imageUrl,
      String linkUrl,
      String locale,
      LocalDateTime startAt,
      LocalDateTime endAt
  ) {}

  public record BannerResponse(
      Long id,
      String name,
      String position,
      String status,
      String imageUrl,
      String linkUrl,
      String locale,
      String startAt,
      String endAt,
      String createdBy,
      String updatedBy,
      String createdAt,
      String updatedAt
  ) {}
}
