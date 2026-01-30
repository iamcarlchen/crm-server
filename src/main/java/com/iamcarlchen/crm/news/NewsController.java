package com.iamcarlchen.crm.news;

import com.iamcarlchen.crm.news.NewsDtos.NewsResponse;
import com.iamcarlchen.crm.news.NewsDtos.NewsUpsertRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
public class NewsController {
  private final NewsService service;

  public NewsController(NewsService service) {
    this.service = service;
  }

  @GetMapping
  public List<NewsResponse> list(@RequestParam(required = false) String status) {
    return service.list(status).stream().map(NewsController::toResponse).toList();
  }

  @GetMapping("/{id}")
  public NewsResponse get(@PathVariable Long id) {
    return toResponse(service.get(id));
  }

  @PostMapping
  public NewsResponse create(@Valid @RequestBody NewsUpsertRequest req) {
    return toResponse(service.create(req));
  }

  @PutMapping("/{id}")
  public NewsResponse update(@PathVariable Long id, @Valid @RequestBody NewsUpsertRequest req) {
    return toResponse(service.update(id, req));
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }

  static NewsResponse toResponse(NewsEntity n) {
    return new NewsResponse(
        n.getId(),
        n.getTitle(),
        n.getSource(),
        n.getSourceUrl(),
        n.getSummary(),
        n.getContent(),
        n.getStatus(),
        n.getPublishedAt() == null ? null : n.getPublishedAt().toString(),
        n.getCreatedAt() == null ? null : n.getCreatedAt().toString(),
        n.getUpdatedAt() == null ? null : n.getUpdatedAt().toString()
    );
  }
}
