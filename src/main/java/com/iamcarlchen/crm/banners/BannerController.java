package com.iamcarlchen.crm.banners;

import com.iamcarlchen.crm.auth.EmployeePrincipal;
import com.iamcarlchen.crm.banners.BannerDtos.BannerResponse;
import com.iamcarlchen.crm.banners.BannerDtos.BannerUpsertRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/banners")
public class BannerController {
  private final BannerService service;

  public BannerController(BannerService service) {
    this.service = service;
  }

  @GetMapping
  public List<BannerResponse> list(
      @RequestParam(required = false) String status,
      @RequestParam(required = false) String position
  ) {
    return service.list(status, position).stream().map(BannerController::toResponse).toList();
  }

  @GetMapping("/{id}")
  public BannerResponse get(@PathVariable Long id) {
    return toResponse(service.get(id));
  }

  @PostMapping
  public BannerResponse create(@Valid @RequestBody BannerUpsertRequest req, Authentication auth) {
    return toResponse(service.create(req, actor(auth)));
  }

  @PutMapping("/{id}")
  public BannerResponse update(@PathVariable Long id, @Valid @RequestBody BannerUpsertRequest req, Authentication auth) {
    return toResponse(service.update(id, req, actor(auth)));
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }

  static BannerResponse toResponse(BannerEntity b) {
    return new BannerResponse(
        b.getId(),
        b.getName(),
        b.getPosition(),
        b.getStatus(),
        b.getImageUrl(),
        b.getLinkUrl(),
        b.getLocale(),
        b.getStartAt() == null ? null : b.getStartAt().toString(),
        b.getEndAt() == null ? null : b.getEndAt().toString(),
        b.getCreatedBy(),
        b.getUpdatedBy(),
        b.getCreatedAt() == null ? null : b.getCreatedAt().toString(),
        b.getUpdatedAt() == null ? null : b.getUpdatedAt().toString()
    );
  }

  private static String actor(Authentication auth) {
    if (auth == null) return null;
    if (auth.getPrincipal() instanceof EmployeePrincipal p) return p.username();
    return auth.getName();
  }
}
