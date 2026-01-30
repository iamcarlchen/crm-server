CREATE TABLE banners (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  position VARCHAR(64) NOT NULL,
  status VARCHAR(32) NOT NULL DEFAULT 'DRAFT',
  image_url VARCHAR(1024),
  link_url VARCHAR(1024),
  locale VARCHAR(64),
  start_at DATETIME NULL,
  end_at DATETIME NULL,
  created_by VARCHAR(64),
  updated_by VARCHAR(64),
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY idx_banners_status (status),
  KEY idx_banners_position (position),
  KEY idx_banners_updated_at (updated_at)
);
