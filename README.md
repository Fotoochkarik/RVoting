Graduation project for Topjava 
===============================
https://javaops.ru/view/topjava2

Designed and implemented a REST API using Hibernate/Spring/ Spring-Boot without frontend.

Voting system for deciding where to have lunch.

Two types of users: admin and regular users
- Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
- Menu changes each day (admins do the updates)
- Users can vote on which restaurant they want to have lunch at
- Only one vote counted per user
- If user votes again the same day: 
    + If it is before 11:00 we assume that he changed his mind.
    + If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

[REST API documentation](http://localhost:9080/rvoting/swagger-ui.html)

 Use credentials for:
- _User:_ (username: user@yandex.ru, password: password)
- _Admin:_ (username: admin@gmail.com, password: admin)
