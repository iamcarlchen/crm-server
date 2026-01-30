package com.iamcarlchen.crm.banners;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "banners")
@Getter
@Setter
public class BannerEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String position; // e.g. HOME_TOP, HOME_MID

  @Column(nullable = false)
  private String status; // DRAFT / ONLINE / OFFLINE

  @Column(name = "image_url", length = 1024)
  private String imageUrl;

  @Column(name = "link_url", length = 1024)
  private String linkUrl;

  @Column(length = 64)
  private String locale; // e.g. zh-CN / en-US; optional

  @Column(name = "start_at")
  private LocalDateTime startAt;

  @Column(name = "end_at")
  private LocalDateTime endAt;

  @Column(name = "created_by", length = 64)
  private String createdBy;

  @Column(name = "updated_by", length = 64)
  private String updatedBy;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @PrePersist
  void prePersist() {
    var now = LocalDateTime.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  @PreUpdate
  void preUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
}
