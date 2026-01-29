package com.iamcarlchen.crm.customers;

import com.iamcarlchen.crm.customers.CustomerDtos.CustomerResponse;
import com.iamcarlchen.crm.customers.CustomerDtos.CustomerUpsertRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
  private final CustomerService service;

  public CustomerController(CustomerService service) {
    this.service = service;
  }

  @GetMapping
  public List<CustomerResponse> list() {
    return service.list().stream().map(CustomerController::toResponse).toList();
  }

  @GetMapping("/{id}")
  public CustomerResponse get(@PathVariable Long id) {
    return toResponse(service.get(id));
  }

  @PostMapping
  public CustomerResponse create(@Valid @RequestBody CustomerUpsertRequest req) {
    return toResponse(service.create(req));
  }

  @PutMapping("/{id}")
  public CustomerResponse update(@PathVariable Long id, @Valid @RequestBody CustomerUpsertRequest req) {
    return toResponse(service.update(id, req));
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }

  static CustomerResponse toResponse(Customer c) {
    return new CustomerResponse(
        c.getId(),
        c.getName(),
        c.getIndustry(),
        c.getLevel(),
        c.getPhone(),
        c.getEmail(),
        c.getAddress(),
        c.getOwner(),
        c.getCreatedAt() == null ? null : c.getCreatedAt().toString(),
        c.getUpdatedAt() == null ? null : c.getUpdatedAt().toString());
  }
}
