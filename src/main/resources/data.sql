INSERT INTO Role (name, deleted, created_at, modified_at)
VALUES
  ('ADMIN', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('CLIENTE', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO job (name, description, expected_duration_minutes, price, deleted, created_at, modified_at)
VALUES
  ('Corte de Cabelo Masculino', 'Serviço de corte padrão masculino com acabamento', 30, 35.00, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Manicure Completa', 'Limpeza, corte e esmaltação das unhas', 60, 50.00, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (role_id, created_at, date_of_birth, modified_at, email, password, phone, username, deleted)
VALUES (1, '2025-04-29 17:56:15.430192', '2025-04-01 00:00:00', '2025-04-29 17:56:15.430192', 'aura@gmail.com','$2a$10$8vpObEvYrKb1/wWqDk1Rmu8At3QsvfOOejOP8hYPPYWMPbr7uwJNa', '11977972747', 'AURA', FALSE);

INSERT INTO scheduling_settings (id, days_of_week, work_start, work_end, break_start, break_end)
VALUES (1, 'TERCA', '08:00:00', '18:00:00', '12:00:00', '13:00:00');