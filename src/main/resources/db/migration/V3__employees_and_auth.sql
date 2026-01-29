CREATE TABLE employees (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(64) NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  phone VARCHAR(64),
  email VARCHAR(255),
  role VARCHAR(32) NOT NULL DEFAULT 'USER',
  status VARCHAR(32) NOT NULL DEFAULT 'ACTIVE',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_employees_username (username)
);

-- seed an initial admin user (password = admin123)
INSERT INTO employees(username, password_hash, name, role, status)
VALUES(
  'admin',
  '$2a$10$8Sga3dOIMs68o0a6xxw2Qu4g2L2d1hCzQ5PjJrH6YAhfW3eQ4m6hK',
  'Admin',
  'ADMIN',
  'ACTIVE'
);
