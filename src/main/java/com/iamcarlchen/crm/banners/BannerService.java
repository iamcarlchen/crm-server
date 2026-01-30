package com.iamcarlchen.crm.banners;

import com.iamcarlchen.crm.common.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BannerService {
  private final BannerRepository repo;

  public BannerService(BannerRepository repo) {
    this.repo = repo;
  }

  public List<BannerEntity> list(String status, String position) {
    boolean hasStatus = status != null && !status.isBlank();
    boolean hasPosition = position != null && !position.isBlank();

    if (hasStatus && hasPosition) {
      return repo.findByStatusAndPositionOrderByUpdatedAtDesc(status, position);
    }
    if (hasStatus) {
      return repo.findByStatusOrderByUpdatedAtDesc(status);
    }
    if (hasPosition) {
      return repo.findByPositionOrderByUpdatedAtDesc(position);
    }
    return repo.findAllByOrderByUpdatedAtDesc();
  }

  public BannerEntity get(Long id) {
    return repo.findById(id).orElseThrow(() -> new NotFoundException("Banner not found: " + id));
  }

  public BannerEntity create(BannerDtos.BannerUpsertRequest req, String actor) {
    var b = new BannerEntity();
    apply(b, req);
    b.setCreatedBy(actor);
    b.setUpdatedBy(actor);
    return repo.save(b);
  }

  public BannerEntity update(Long id, BannerDtos.BannerUpsertRequest req, String actor) {
    var b = get(id);
    apply(b, req);
    b.setUpdatedBy(actor);
    return repo.save(b);
  }

  public void delete(Long id) {
    if (!repo.existsById(id)) throw new NotFoundException("Banner not found: " + id);
    repo.deleteById(id);
  }

  private static void apply(BannerEntity b, BannerDtos.BannerUpsertRequest req) {
    b.setName(req.name());
    b.setPosition(req.position());
    b.setStatus(req.status());
    b.setImageUrl(req.imageUrl());
    b.setLinkUrl(req.linkUrl());
    b.setLocale(req.locale());
    b.setStartAt(req.startAt());
    b.setEndAt(req.endAt());
  }
}
