package com.iamcarlchen.crm.news;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class NewsDtos {

  public record NewsUpsertRequest(
      @NotBlank String title,
      String source,
      String sourceUrl,
      String summary,
      String content,
      @NotBlank String status,
      LocalDateTime publishedAt
  ) {}

  public record NewsResponse(
      Long id,
      String title,
      String source,
      String sourceUrl,
      String summary,
      String content,
      String status,
      String publishedAt,
      String createdAt,
      String updatedAt
  ) {}
}
