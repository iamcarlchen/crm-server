CREATE TABLE customers (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  industry VARCHAR(255),
  level CHAR(1) NOT NULL,
  phone VARCHAR(64),
  email VARCHAR(255),
  address VARCHAR(512),
  owner VARCHAR(255),
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE orders (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  customer_id BIGINT NOT NULL,
  title VARCHAR(255) NOT NULL,
  amount DECIMAL(12,2) NOT NULL DEFAULT 0,
  status VARCHAR(32) NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_orders_customer FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE visits (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  customer_id BIGINT NOT NULL,
  visit_date DATE NOT NULL,
  method VARCHAR(32) NOT NULL,
  summary TEXT NOT NULL,
  next_action TEXT,
  owner VARCHAR(255),
  CONSTRAINT fk_visits_customer FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE finance_records (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  customer_id BIGINT NOT NULL,
  record_type VARCHAR(32) NOT NULL,
  amount DECIMAL(12,2) NOT NULL DEFAULT 0,
  record_date DATE NOT NULL,
  status VARCHAR(32) NOT NULL,
  note TEXT,
  CONSTRAINT fk_finance_customer FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE INDEX idx_orders_customer_id ON orders(customer_id);
CREATE INDEX idx_visits_customer_id ON visits(customer_id);
CREATE INDEX idx_finance_customer_id ON finance_records(customer_id);
