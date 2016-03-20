DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (description, calories, datetime, owner_id) VALUES
  ('User breakfast #1', 500, '20160319 09:00:00', 100000),
  ('User lunch #1', 1000, '20160319 12:00:00', 100000),
  ('User dinner #1', 500, '20160319 19:00:00', 100000),
  ('User breakfast #2', 700, '20160320 09:00:00', 100000),
  ('User lunch #2', 1000, '20160320 12:00:00', 100000),
  ('User dinner #2', 500, '20160320 19:30:00', 100000),
  ('User breakfast #3', 700, '20160321 09:00:00', 100000),
  ('User lunch #3', 1000, '20160321 12:00:00', 100000),
  ('User dinner #3', 500, '20160321 19:30:00', 100000),
  ('Admin breakfast #1', 555, '20160319 09:00:00', 100001),
  ('Admin lunch #1', 900, '20160319 12:00:00', 100001),
  ('Admin dinner #1', 450, '20160319 18:30:00', 100001),
  ('Admin breakfast #2', 800, '20160320 09:00:00', 100001),
  ('Admin lunch #2', 950, '20160320 12:00:00', 100001),
  ('Admin dinner #2', 600, '20160320 19:00:00', 100001);