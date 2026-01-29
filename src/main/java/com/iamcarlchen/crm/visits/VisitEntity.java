package com.iamcarlchen.crm.visits;

import com.iamcarlchen.crm.customers.Customer;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "visits")
@Getter
@Setter
public class VisitEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;

  @Column(name = "visit_date", nullable = false)
  private LocalDate date;

  @Column(nullable = false)
  private String method;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String summary;

  @Column(name = "next_action", columnDefinition = "TEXT")
  private String nextAction;

  private String owner;
}
