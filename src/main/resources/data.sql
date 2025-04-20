INSERT INTO Role (name, deleted, created_at, modified_at)
VALUES
  ('ADMIN', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('CLIENTE', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO job (name, description, expected_duration_minutes, price, deleted, created_at, modified_at)
VALUES
  ('Corte de Cabelo Masculino', 'Serviço de corte padrão masculino com acabamento', 30, 35.00, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Manicure Completa', 'Limpeza, corte e esmaltação das unhas', 45, 50.00, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (username, email, password, phone, role_id, deleted, created_at, modified_at)
VALUES ('luiz', 'luiz@email.com', '1234', '11999999999', 1, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);