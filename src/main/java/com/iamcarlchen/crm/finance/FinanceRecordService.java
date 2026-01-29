package com.iamcarlchen.crm.finance;

import com.iamcarlchen.crm.common.NotFoundException;
import com.iamcarlchen.crm.customers.CustomerService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FinanceRecordService {
  private final FinanceRecordRepository repo;
  private final CustomerService customerService;

  public FinanceRecordService(FinanceRecordRepository repo, CustomerService customerService) {
    this.repo = repo;
    this.customerService = customerService;
  }

  public List<FinanceRecordEntity> list(Long customerId) {
    if (customerId == null) return repo.findAll();
    return repo.findByCustomerId(customerId);
  }

  public FinanceRecordEntity get(Long id) {
    return repo.findById(id)
        .orElseThrow(() -> new NotFoundException("Finance record not found: " + id));
  }

  public FinanceRecordEntity create(FinanceRecordDtos.FinanceRecordUpsertRequest req) {
    var customer = customerService.get(req.customerId());
    var r = new FinanceRecordEntity();
    r.setCustomer(customer);
    apply(r, req);
    return repo.save(r);
  }

  public FinanceRecordEntity update(Long id, FinanceRecordDtos.FinanceRecordUpsertRequest req) {
    var r = get(id);
    var customer = customerService.get(req.customerId());
    r.setCustomer(customer);
    apply(r, req);
    return repo.save(r);
  }

  public void delete(Long id) {
    if (!repo.existsById(id)) throw new NotFoundException("Finance record not found: " + id);
    repo.deleteById(id);
  }

  private void apply(FinanceRecordEntity r, FinanceRecordDtos.FinanceRecordUpsertRequest req) {
    r.setType(req.type());
    r.setAmount(req.amount());
    r.setDate(req.date());
    r.setStatus(req.status());
    r.setNote(req.note());
  }
}
