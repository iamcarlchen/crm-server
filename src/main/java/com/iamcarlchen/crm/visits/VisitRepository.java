package com.iamcarlchen.crm.visits;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<VisitEntity, Long> {
  List<VisitEntity> findByCustomerId(Long customerId);
}
