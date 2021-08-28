INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT(name)
VALUES ('Macdonald`s'),
       ('KFC'),
       ('Burger King');

INSERT INTO DISH (name, price, restaurant_id)
VALUES ('Breakfast', 123, 1),
       ('Lunch', 100, 1),
       ('Dish1', 100, 2),
       ('Dish2', 101, 2),
       ('Dish3', 102, 2),
       ('Dish4', 103, 2),
       ('Dish5', 104, 2),
       ('Dish6', 105, 3),
       ('Dish7', 106, 3),
       ('Dish8', 107, 3),
       ('Dish9', 108, 3),
       ('Dish10', 109, 3);

