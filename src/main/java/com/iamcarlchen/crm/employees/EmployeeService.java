package com.iamcarlchen.crm.employees;

import com.iamcarlchen.crm.common.NotFoundException;
import jakarta.validation.ValidationException;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeService {
  private final EmployeeRepository repo;
  private final PasswordEncoder passwordEncoder;

  public EmployeeService(EmployeeRepository repo, PasswordEncoder passwordEncoder) {
    this.repo = repo;
    this.passwordEncoder = passwordEncoder;
  }

  public List<Employee> list() {
    return repo.findAll();
  }

  public Employee get(Long id) {
    return repo.findById(id).orElseThrow(() -> new NotFoundException("Employee not found: " + id));
  }

  public Employee getByUsername(String username) {
    return repo.findByUsername(username)
        .orElseThrow(() -> new NotFoundException("Employee not found: " + username));
  }

  public Employee create(EmployeeDtos.EmployeeUpsertRequest req) {
    if (repo.existsByUsername(req.username())) {
      throw new ValidationException("username already exists");
    }

    var e = new Employee();
    e.setUsername(req.username());
    e.setName(req.name());
    e.setPhone(req.phone());
    e.setEmail(req.email());
    e.setRole(req.role() == null || req.role().isBlank() ? "USER" : req.role());
    e.setStatus(req.status() == null || req.status().isBlank() ? "ACTIVE" : req.status());

    var raw = req.password();
    if (raw == null || raw.isBlank()) {
      raw = "changeme";
    }
    e.setPasswordHash(passwordEncoder.encode(raw));

    return repo.save(e);
  }

  public Employee update(Long id, EmployeeDtos.EmployeeUpsertRequest req) {
    var e = get(id);

    // username immutable for simplicity
    e.setName(req.name());
    e.setPhone(req.phone());
    e.setEmail(req.email());
    if (req.role() != null && !req.role().isBlank()) e.setRole(req.role());
    if (req.status() != null && !req.status().isBlank()) e.setStatus(req.status());

    if (req.password() != null && !req.password().isBlank()) {
      e.setPasswordHash(passwordEncoder.encode(req.password()));
    }

    return repo.save(e);
  }

  public void delete(Long id) {
    if (!repo.existsById(id)) throw new NotFoundException("Employee not found: " + id);
    repo.deleteById(id);
  }

  public boolean verifyPassword(Employee e, String rawPassword) {
    return passwordEncoder.matches(rawPassword, e.getPasswordHash());
  }
}
