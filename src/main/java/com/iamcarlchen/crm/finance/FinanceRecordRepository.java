package com.iamcarlchen.crm.finance;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceRecordRepository extends JpaRepository<FinanceRecordEntity, Long> {
  List<FinanceRecordEntity> findByCustomerId(Long customerId);
}
