package com.iamcarlchen.crm.finance;

import com.iamcarlchen.crm.customers.Customer;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "finance_records")
@Getter
@Setter
public class FinanceRecordEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;

  @Column(name = "record_type", nullable = false)
  private String type;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal amount;

  @Column(name = "record_date", nullable = false)
  private LocalDate date;

  @Column(nullable = false)
  private String status;

  @Column(columnDefinition = "TEXT")
  private String note;
}
