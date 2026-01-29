package com.iamcarlchen.crm.finance;

import com.iamcarlchen.crm.finance.FinanceRecordDtos.FinanceRecordResponse;
import com.iamcarlchen.crm.finance.FinanceRecordDtos.FinanceRecordUpsertRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/finance-records")
public class FinanceRecordController {
  private final FinanceRecordService service;

  public FinanceRecordController(FinanceRecordService service) {
    this.service = service;
  }

  @GetMapping
  public List<FinanceRecordResponse> list(@RequestParam(required = false) Long customerId) {
    return service.list(customerId).stream().map(FinanceRecordController::toResponse).toList();
  }

  @GetMapping("/{id}")
  public FinanceRecordResponse get(@PathVariable Long id) {
    return toResponse(service.get(id));
  }

  @PostMapping
  public FinanceRecordResponse create(@Valid @RequestBody FinanceRecordUpsertRequest req) {
    return toResponse(service.create(req));
  }

  @PutMapping("/{id}")
  public FinanceRecordResponse update(@PathVariable Long id, @Valid @RequestBody FinanceRecordUpsertRequest req) {
    return toResponse(service.update(id, req));
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }

  static FinanceRecordResponse toResponse(FinanceRecordEntity r) {
    return new FinanceRecordResponse(
        r.getId(),
        r.getCustomer().getId(),
        r.getCustomer().getName(),
        r.getType(),
        r.getAmount(),
        r.getDate(),
        r.getStatus(),
        r.getNote());
  }
}
