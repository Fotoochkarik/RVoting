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

INSERT INTO VOTE (date_creation, restaurant_id, user_id, limit_time)
VALUES ('2021-08-31', 1, 1, '11:00:00'),
       ('2021-08-30', 2, 2, '11:00:00'),
       ('2021-08-30', 3, 2, '11:00:00');