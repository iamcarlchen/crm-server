package com.iamcarlchen.crm.orders;

import com.iamcarlchen.crm.orders.OrderDtos.OrderResponse;
import com.iamcarlchen.crm.orders.OrderDtos.OrderUpsertRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final OrderService service;

  public OrderController(OrderService service) {
    this.service = service;
  }

  @GetMapping
  public List<OrderResponse> list(@RequestParam(required = false) Long customerId) {
    return service.list(customerId).stream().map(OrderController::toResponse).toList();
  }

  @GetMapping("/{id}")
  public OrderResponse get(@PathVariable Long id) {
    return toResponse(service.get(id));
  }

  @PostMapping
  public OrderResponse create(@Valid @RequestBody OrderUpsertRequest req) {
    return toResponse(service.create(req));
  }

  @PutMapping("/{id}")
  public OrderResponse update(@PathVariable Long id, @Valid @RequestBody OrderUpsertRequest req) {
    return toResponse(service.update(id, req));
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }

  static OrderResponse toResponse(OrderEntity o) {
    return new OrderResponse(
        o.getId(),
        o.getCustomer().getId(),
        o.getCustomer().getName(),
        o.getTitle(),
        o.getAmount(),
        o.getStatus(),
        o.getCreatedAt() == null ? null : o.getCreatedAt().toString());
  }
}
