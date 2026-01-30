package com.iamcarlchen.crm.news;

import com.iamcarlchen.crm.common.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NewsService {
  private final NewsRepository repo;

  public NewsService(NewsRepository repo) {
    this.repo = repo;
  }

  public List<NewsEntity> list(String status) {
    if (status == null || status.isBlank()) {
      return repo.findAllByOrderByUpdatedAtDesc();
    }
    return repo.findByStatusOrderByUpdatedAtDesc(status);
  }

  public NewsEntity get(Long id) {
    return repo.findById(id).orElseThrow(() -> new NotFoundException("News not found: " + id));
  }

  public NewsEntity create(NewsDtos.NewsUpsertRequest req) {
    var n = new NewsEntity();
    apply(n, req);
    return repo.save(n);
  }

  public NewsEntity update(Long id, NewsDtos.NewsUpsertRequest req) {
    var n = get(id);
    apply(n, req);
    return repo.save(n);
  }

  public void delete(Long id) {
    if (!repo.existsById(id)) throw new NotFoundException("News not found: " + id);
    repo.deleteById(id);
  }

  private static void apply(NewsEntity n, NewsDtos.NewsUpsertRequest req) {
    n.setTitle(req.title());
    n.setSource(req.source());
    n.setSourceUrl(req.sourceUrl());
    n.setSummary(req.summary());
    n.setContent(req.content());
    n.setStatus(req.status());
    n.setPublishedAt(req.publishedAt());
  }
}
