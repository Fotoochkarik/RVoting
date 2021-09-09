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
       ('Burger King'),
       ('Burger Lab'),
       ('Hesburger'),
       ('Carl’s Jr.'),
       ('Black Star Burger');

INSERT INTO DISH (date_of_use, name, price, restaurant_id)
VALUES ('2021-08-30', 'Burger', 123, 1),
       ('2021-08-30', 'Big Burger', 100, 1),
       ('2021-08-29', 'Dish1', 100, 2),
       ('2021-08-29', 'Dish2', 101, 2),
       ('2021-08-29', 'Dish3', 102, 2),
       ('2021-08-30', 'Dish4', 103, 3),
       ('2021-08-30', 'Dish5', 104, 3),
       ('2021-08-30', 'Dish6', 105, 3),
       (now(), 'Dish7', 106, 4),
       (now(), 'Dish8', 107, 4),
       (now(), 'Dish9', 108, 4),
       (now(), 'Dish10', 109, 4),
       (now(), 'Dish11', 110, 5),
       (now(), 'Dish12', 1018, 5),
       (now(), 'Dish13', 111, 5),
       (now(), 'Dish14', 1123, 5),
       (now(), 'Dish15', 112, 6),
       (now(), 'Dish16', 113, 6),
       (now(), 'Dish17', 115, 6),
       (now(), 'Dish18', 116, 6),
       (now(), 'Dish19', 117, 7),
       (now(), 'Dish20', 118, 7),
       (now(), 'Dish21', 119, 7);

INSERT INTO VOTE (user_id, restaurant_id, date_creation)
VALUES (1, 1, '2021-08-30'),
       (2, 1, '2021-08-30'),
       (2, 3, '2021-08-31');