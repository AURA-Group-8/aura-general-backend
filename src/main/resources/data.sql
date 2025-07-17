INSERT INTO Role (name, deleted, created_at, modified_at)
VALUES ('ADMIN', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('CLIENTE', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO job (name, description, expected_duration_minutes, price, deleted, created_at, modified_at)
VALUES ('Design com Henna', 'Design de sobrancelhas com aplicação de henna.', 60, 55.00, false, CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       ('Design Personalizado', 'Design de sobrancelhas feito de forma personalizada conforme o formato do rosto.', 30,
        40.00, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Design com Tintura', 'Design de sobrancelhas com aplicação de tintura para definição e realce da cor.', 60,
        60.00, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Brow Lamination', 'Alinhamento e fixação dos fios das sobrancelhas para um efeito de volume e definição.', 120,
        160.00, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Lash Lifting', 'Curvatura dos cílios naturais, promovendo efeito de alongamento e definição.', 180, 150.00,
        false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Epilação Buço', 'Remoção dos pelos da região do buço com técnica de epilação.', 30, 15.00, false,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Hydragloss', 'Revitalização profunda dos lábios com hidratação intensiva.', 30, 120.00, false,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Nanopigmentação',
        'Técnica semi-definitiva com duração de 6 meses a 1 ano. Pelos artificiais são desenhados para cobrir falhas e harmonizar as sobrancelhas naturalmente.',
        180, 600.00, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Shadow',
        'Técnica semi-definitiva com sombreamento semelhante à henna. Cobre falhas e harmoniza as sobrancelhas com duração de 8 meses a 1 ano e meio.',
        180, 550.00, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (role_id, created_at, date_of_birth, modified_at, email, password, phone, username, deleted)
VALUES (1, '2025-04-29 17:56:15.430192', '2025-04-01 00:00:00', '2025-04-29 17:56:15.430192', 'aura@gmail.com',
        '$2a$10$8vpObEvYrKb1/wWqDk1Rmu8At3QsvfOOejOP8hYPPYWMPbr7uwJNa', '11977972747', 'AURA', FALSE),
       (2, '2025-04-29 17:56:15.430192', '1998-07-15', '2025-04-29 17:56:15.430192', 'jaqueline@gmail.com',
        '$2a$10$8vpObEvYrKb1/wWqDk1Rmu8At3QsvfOOejOP8hYPPYWMPbr7uwJNa', '11999990001', 'JAQUELINE', FALSE),
       (2, '2025-04-29 17:56:15.430192', '2000-01-22', '2025-04-29 17:56:15.430192', 'carol@hotmail.com',
        '$2a$10$8vpObEvYrKb1/wWqDk1Rmu8At3QsvfOOejOP8hYPPYWMPbr7uwJNa', '11999990002', 'CAROL', FALSE),
       (2, '2025-04-29 17:56:15.430192', '1995-10-03', '2025-04-29 17:56:15.430192', 'luiza@gmail.com',
        '$2a$10$8vpObEvYrKb1/wWqDk1Rmu8At3QsvfOOejOP8hYPPYWMPbr7uwJNa', '11999990003', 'LUIZA', FALSE),
       (2, '2025-04-29 17:56:15.430192', '1997-03-08', '2025-04-29 17:56:15.430192', 'marcela@gmail.com',
        '$2a$10$8vpObEvYrKb1/wWqDk1Rmu8At3QsvfOOejOP8hYPPYWMPbr7uwJNa', '11999990004', 'MARCELA', FALSE),
       (2, '2025-04-29 17:56:15.430192', '1999-11-27', '2025-04-29 17:56:15.430192', 'natalia@gmail.com',
        '$2a$10$8vpObEvYrKb1/wWqDk1Rmu8At3QsvfOOejOP8hYPPYWMPbr7uwJNa', '11999990005', 'NATALIA', FALSE);

INSERT INTO scheduling_settings (id, days_of_week, work_start, work_end, break_start, break_end)
VALUES (1, 'SEGUNDA', '08:00:00', '18:00:00', '12:00:00', '13:00:00');

INSERT INTO scheduling (feedback, is_canceled, total_price, users_id, canceled_at, created_at, end_datetime,
                        modified_at, start_datetime, payment_status, status)
VALUES (5, false, 215.00, 2, NULL, '2025-06-09 10:00:00', '2025-06-09 11:00:00', '2025-06-09 11:00:00',
        '2025-06-09 10:00:00', 'PAGO', 'FEITO'),
       (4, false, 725.00, 4, NULL, '2025-06-09 14:00:00', '2025-06-09 15:30:00', '2025-06-09 15:30:00',
        '2025-06-09 14:00:00', 'PAGO', 'FEITO'),
       (5, false, 115.00, 5, NULL, '2025-06-10 09:00:00', '2025-06-10 09:45:00', '2025-06-10 09:45:00',
        '2025-06-10 09:00:00', 'PAGO', 'FEITO'),
       (5, false, 750.00, 6, NULL, '2025-06-12 13:00:00', '2025-06-12 14:30:00', '2025-06-12 14:30:00',
        '2025-06-12 13:00:00', 'PAGO', 'FEITO');

INSERT INTO job_scheduling (scheduling_id, job_id, current_price, observations, discount_applied)
VALUES
(1, 4, 160.00, '', false),
(1, 6, 15.00, '', false),
(1, 2, 40.00, '', false),
(2, 9, 550.00, '', false),
(2, 7, 120.00, '', false),
(2, 1, 55.00, '', false),
(3, 1, 55.00, '', false),
(3, 3, 60.00, '', false),
(4, 8, 600.00, '', false),
(4, 5, 150.00, '', false);