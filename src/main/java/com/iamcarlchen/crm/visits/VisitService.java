package com.iamcarlchen.crm.visits;

import com.iamcarlchen.crm.common.NotFoundException;
import com.iamcarlchen.crm.customers.CustomerService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VisitService {
  private final VisitRepository repo;
  private final CustomerService customerService;

  public VisitService(VisitRepository repo, CustomerService customerService) {
    this.repo = repo;
    this.customerService = customerService;
  }

  public List<VisitEntity> list(Long customerId) {
    if (customerId == null) return repo.findAll();
    return repo.findByCustomerId(customerId);
  }

  public VisitEntity get(Long id) {
    return repo.findById(id).orElseThrow(() -> new NotFoundException("Visit not found: " + id));
  }

  public VisitEntity create(VisitDtos.VisitUpsertRequest req) {
    var customer = customerService.get(req.customerId());
    var v = new VisitEntity();
    v.setCustomer(customer);
    apply(v, req);
    return repo.save(v);
  }

  public VisitEntity update(Long id, VisitDtos.VisitUpsertRequest req) {
    var v = get(id);
    var customer = customerService.get(req.customerId());
    v.setCustomer(customer);
    apply(v, req);
    return repo.save(v);
  }

  public void delete(Long id) {
    if (!repo.existsById(id)) throw new NotFoundException("Visit not found: " + id);
    repo.deleteById(id);
  }

  private void apply(VisitEntity v, VisitDtos.VisitUpsertRequest req) {
    v.setDate(req.date());
    v.setMethod(req.method());
    v.setSummary(req.summary());
    v.setNextAction(req.nextAction());
    v.setOwner(req.owner());
  }
}
