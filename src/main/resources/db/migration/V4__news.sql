CREATE TABLE news (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  source VARCHAR(255),
  source_url VARCHAR(1024),
  summary VARCHAR(1024),
  content TEXT,
  status VARCHAR(32) NOT NULL DEFAULT 'DRAFT',
  published_at DATETIME NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY idx_news_status (status),
  KEY idx_news_updated_at (updated_at)
);
