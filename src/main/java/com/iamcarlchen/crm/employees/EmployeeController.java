package com.iamcarlchen.crm.employees;

import com.iamcarlchen.crm.employees.EmployeeDtos.EmployeeResponse;
import com.iamcarlchen.crm.employees.EmployeeDtos.EmployeeUpsertRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
  private final EmployeeService service;

  public EmployeeController(EmployeeService service) {
    this.service = service;
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<EmployeeResponse> list() {
    return service.list().stream().map(EmployeeController::toResponse).toList();
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public EmployeeResponse get(@PathVariable Long id) {
    return toResponse(service.get(id));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public EmployeeResponse create(@Valid @RequestBody EmployeeUpsertRequest req) {
    return toResponse(service.create(req));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public EmployeeResponse update(@PathVariable Long id, @Valid @RequestBody EmployeeUpsertRequest req) {
    return toResponse(service.update(id, req));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }

  static EmployeeResponse toResponse(Employee e) {
    return new EmployeeResponse(
        e.getId(),
        e.getUsername(),
        e.getName(),
        e.getPhone(),
        e.getEmail(),
        e.getRole(),
        e.getStatus(),
        e.getCreatedAt() == null ? null : e.getCreatedAt().toString(),
        e.getUpdatedAt() == null ? null : e.getUpdatedAt().toString());
  }
}
