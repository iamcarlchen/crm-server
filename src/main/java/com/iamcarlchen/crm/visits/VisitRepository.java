package com.iamcarlchen.crm.visits;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<VisitEntity, Long> {

  // Avoid LazyInitializationException when mapping entities to DTOs outside a transaction.
  @Override
  @EntityGraph(attributePaths = "customer")
  List<VisitEntity> findAll();

  @EntityGraph(attributePaths = "customer")
  List<VisitEntity> findByCustomerId(Long customerId);
}
