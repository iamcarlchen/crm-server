package com.iamcarlchen.crm.orders;

import com.iamcarlchen.crm.common.NotFoundException;
import com.iamcarlchen.crm.customers.CustomerService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
  private final OrderRepository repo;
  private final CustomerService customerService;

  public OrderService(OrderRepository repo, CustomerService customerService) {
    this.repo = repo;
    this.customerService = customerService;
  }

  public List<OrderEntity> list(Long customerId) {
    if (customerId == null) return repo.findAll();
    return repo.findByCustomerId(customerId);
  }

  public OrderEntity get(Long id) {
    return repo.findById(id).orElseThrow(() -> new NotFoundException("Order not found: " + id));
  }

  public OrderEntity create(OrderDtos.OrderUpsertRequest req) {
    var customer = customerService.get(req.customerId());
    var o = new OrderEntity();
    o.setCustomer(customer);
    o.setTitle(req.title());
    o.setAmount(req.amount());
    o.setStatus(req.status());
    return repo.save(o);
  }

  public OrderEntity update(Long id, OrderDtos.OrderUpsertRequest req) {
    var o = get(id);
    var customer = customerService.get(req.customerId());
    o.setCustomer(customer);
    o.setTitle(req.title());
    o.setAmount(req.amount());
    o.setStatus(req.status());
    return repo.save(o);
  }

  public void delete(Long id) {
    if (!repo.existsById(id)) throw new NotFoundException("Order not found: " + id);
    repo.deleteById(id);
  }
}
