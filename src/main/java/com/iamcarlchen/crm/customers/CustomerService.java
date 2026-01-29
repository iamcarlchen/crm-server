package com.iamcarlchen.crm.customers;

import com.iamcarlchen.crm.common.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerService {
  private final CustomerRepository repo;

  public CustomerService(CustomerRepository repo) {
    this.repo = repo;
  }

  public List<Customer> list() {
    return repo.findAll();
  }

  public Customer get(Long id) {
    return repo.findById(id).orElseThrow(() -> new NotFoundException("Customer not found: " + id));
  }

  public Customer create(CustomerDtos.CustomerUpsertRequest req) {
    var c = new Customer();
    apply(c, req);
    return repo.save(c);
  }

  public Customer update(Long id, CustomerDtos.CustomerUpsertRequest req) {
    var c = get(id);
    apply(c, req);
    return repo.save(c);
  }

  public void delete(Long id) {
    if (!repo.existsById(id)) throw new NotFoundException("Customer not found: " + id);
    repo.deleteById(id);
  }

  private void apply(Customer c, CustomerDtos.CustomerUpsertRequest req) {
    c.setName(req.name());
    c.setIndustry(req.industry());
    c.setLevel(req.level());
    c.setPhone(req.phone());
    c.setEmail(req.email());
    c.setAddress(req.address());
    c.setOwner(req.owner());
  }
}
