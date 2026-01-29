package com.iamcarlchen.crm.visits;

import com.iamcarlchen.crm.visits.VisitDtos.VisitResponse;
import com.iamcarlchen.crm.visits.VisitDtos.VisitUpsertRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visits")
public class VisitController {
  private final VisitService service;

  public VisitController(VisitService service) {
    this.service = service;
  }

  @GetMapping
  public List<VisitResponse> list(@RequestParam(required = false) Long customerId) {
    return service.list(customerId).stream().map(VisitController::toResponse).toList();
  }

  @GetMapping("/{id}")
  public VisitResponse get(@PathVariable Long id) {
    return toResponse(service.get(id));
  }

  @PostMapping
  public VisitResponse create(@Valid @RequestBody VisitUpsertRequest req) {
    return toResponse(service.create(req));
  }

  @PutMapping("/{id}")
  public VisitResponse update(@PathVariable Long id, @Valid @RequestBody VisitUpsertRequest req) {
    return toResponse(service.update(id, req));
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }

  static VisitResponse toResponse(VisitEntity v) {
    return new VisitResponse(
        v.getId(),
        v.getCustomer().getId(),
        v.getCustomer().getName(),
        v.getDate(),
        v.getMethod(),
        v.getSummary(),
        v.getNextAction(),
        v.getOwner());
  }
}
