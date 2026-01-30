package com.iamcarlchen.crm.banners;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<BannerEntity, Long> {
  List<BannerEntity> findAllByOrderByUpdatedAtDesc();
  List<BannerEntity> findByStatusOrderByUpdatedAtDesc(String status);
  List<BannerEntity> findByPositionOrderByUpdatedAtDesc(String position);
  List<BannerEntity> findByStatusAndPositionOrderByUpdatedAtDesc(String status, String position);
}
