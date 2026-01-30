package com.iamcarlchen.crm.news;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
  List<NewsEntity> findByStatusOrderByUpdatedAtDesc(String status);
  List<NewsEntity> findAllByOrderByUpdatedAtDesc();
}
