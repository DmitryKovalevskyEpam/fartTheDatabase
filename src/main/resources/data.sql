INSERT INTO users (name, surname, birth_date, email) VALUES
('John', 'Doe', '1990-05-15', 'john.doe@example.com'),
('Jane', 'Smith', '1985-08-20', 'jane.smith@example.com'),
('Michael', 'Johnson', '1992-03-10', 'michael.johnson@example.com'),
('Emily', 'Brown', '1988-11-25', 'emily.brown@example.com');

-- Insert sample card info

INSERT INTO card_info (user_id, number, holder, expiration_date)
VALUES
((SELECT id FROM users WHERE email = 'john.doe@example.com'), '1111-2222-3333-4444', 'JOHN DOE', '2025-12-31'),
((SELECT id FROM users WHERE email = 'john.doe@example.com'), '5555-6666-7777-8888', 'JOHN DOE', '2026-06-30');
INSERT INTO card_info (user_id, number, holder, expiration_date)
VALUES
((SELECT id FROM users WHERE email = 'jane.smith@example.com'), '1111-2222-3333-4444', 'JOHN SMITH', '2025-12-15'),
INSERT INTO card_info (user_id, number, holder, expiration_date)
VALUES
((SELECT id FROM users WHERE email = 'michael.johnson@example.com'), '1111-2222-3333-7777', 'MICHAEL JOHNSON', '2025-09-19'),
INSERT INTO card_info (user_id, number, holder, expiration_date)
VALUES
((SELECT id FROM users WHERE email = 'emily.brown@example.com'), '1111-2222-3333-8888', 'EMILY BROWN', '2025-12-25'),


-- ### Тестовый SQL для проверки каскада
-- Создаем пользователя
INSERT INTO users (name, surname, birth_date, email)
VALUES ('Test', 'User', '1990-01-01', 'test@example.com');

-- Создаем карты для пользователя
INSERT INTO card_info (user_id, number, holder, expiration_date)
VALUES
((SELECT id FROM users WHERE email = 'test@example.com'), '1111-2222-3333-4444', 'TEST USER', '2025-12-31'),
((SELECT id FROM users WHERE email = 'test@example.com'), '5555-6666-7777-8888', 'TEST USER', '2026-06-30');

-- Проверяем каскадное удаление
DELETE FROM users WHERE email = 'test@example.com';

-- Карты должны быть автоматически удалены благодаря CASCADE
SELECT * FROM card_info WHERE holder = 'TEST USER'; -- Должно вернуть 0 записей