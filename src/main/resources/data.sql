INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO DISH (name, price)
VALUES ('Breakfast', 123),
       ('Lunch', 100);

INSERT INTO RESTAURANT(name)
VALUES ('Macdonald`s'),
       ('KFC'),
       ('Burger King');