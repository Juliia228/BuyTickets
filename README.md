# BuyTickets
REST API для предоставления возможностей пользователям покупать транспортные билеты

## Выполненные задачи
1) Реализован микросервис с REST интерфейсом, который обрабатывает заявки на покупку билетов.
2) Созданы основные сущности и реализовано взаимодействие с ними:
[Билет](https://github.com/Juliia228/BuyTickets/blob/main/src/main/java/test/task/stm/BuyTickets/models/Ticket.java),
[Маршрут](https://github.com/Juliia228/BuyTickets/blob/main/src/main/java/test/task/stm/BuyTickets/models/Route.java),
[Перевозчик](https://github.com/Juliia228/BuyTickets/blob/main/src/main/java/test/task/stm/BuyTickets/models/Transporter.java),
[Пользователь](https://github.com/Juliia228/BuyTickets/blob/main/src/main/java/test/task/stm/BuyTickets/models/User.java).
3) Основные REST методы:

- Регистрация нового пользователя - [register](https://github.com/Juliia228/BuyTickets/blob/main/src/main/java/test/task/stm/BuyTickets/controllers/UserController.java)

- Получение списка всех доступных для покупки билетов, с возможностью пагинации и фильтрации - [getTicketsByParams](https://github.com/Juliia228/BuyTickets/blob/main/src/main/java/test/task/stm/BuyTickets/controllers/TicketController.java)

- Покупка определенного билета - [buyTicket](https://github.com/Juliia228/BuyTickets/blob/main/src/main/java/test/task/stm/BuyTickets/controllers/PurchaseController.java)

- Получение списка всех купленных билетов для текущего пользователя - [getBoughtTickets](https://github.com/Juliia228/BuyTickets/blob/main/src/main/java/test/task/stm/BuyTickets/controllers/TicketController.java)

4) Все данные хранятся в СУБД PostgreSQL.
5) Для доступа к бд использовался JdbcTemplate.
6) Входные данные REST методов валидируются.
7) Присутсвует Swagger документация.
8) **Дополнительная задача 1:**
Реализована аутентификация пользователей с помощью JWT токенов (access и refresh токены).
Добавлены методы для аутентификации уже зарегистрированных пользователей и обновления access токена по refresh токену.
9) **Дополнительная задача 2:** Добавлены роли «Покупатель» и «Администратор».

## Информация о проекте
Сервер реализован с помощью `Java, Spring Boot, Spring Security, PostgreSQL, lombok`.

В проект добавлено [тестирование](https://github.com/Juliia228/BuyTickets/tree/main/src/test/java/test/task/stm/BuyTickets). Помимо этого, тестирование осуществлялось с помощью Postman и Swagger UI.

Запуск сервера происходит из файла [BuyTicketsApplication](https://github.com/Juliia228/BuyTickets/blob/main/src/main/java/test/task/stm/BuyTickets/BuyTicketsApplication.java). Сервер запускается локально, порт `9090`.
Сборка - Maven

Для запуска проекта необходимо наличие созданной базы данных в PostgreSQL под пользователем postgres (пароль [здесь](https://github.com/Juliia228/BuyTickets/blob/main/src/main/resources/application.properties)). Для этого в PostgreSQL нужно:
1) Создать базу данных (порт 5433)
```
    CREATE DATABASE BuyTickets
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
```
2) Создать в бд таблицы:
- users
```
CREATE TABLE public.users
(
    id serial NOT NULL,
    login character varying NOT NULL,
    password character varying NOT NULL,
    first_name character varying NOT NULL,
    last_name character varying NOT NULL,
    patronymic character varying,
    roles character varying(50)[],
    PRIMARY KEY (id),
    UNIQUE (login)
);

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;
```
- transporters
```
CREATE TABLE public.transporters
(
    name character varying NOT NULL,
    phone character varying(20),
    PRIMARY KEY (name)
);

ALTER TABLE IF EXISTS public.transporters
    OWNER to postgres;
```
- routes
```
CREATE TABLE public.routes
(
    id serial NOT NULL,
    departure_point character varying NOT NULL,
    destination_point character varying NOT NULL,
    transporter_name character varying NOT NULL,
    minutes integer NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (transporter_name)
        REFERENCES public.transporters (name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS public.routes
    OWNER to postgres;
```
- tickets
```
CREATE TABLE public.tickets
(
    id serial NOT NULL,
    route_id integer NOT NULL,
    departure_at timestamp with time zone NOT NULL,
    seat_number integer,
    price bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (route_id)
        REFERENCES public.routes (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
);

ALTER TABLE IF EXISTS public.tickets
    OWNER to postgres;
```
- purchases
```
CREATE TABLE public.purchases
(
    id serial NOT NULL,
    user_id integer NOT NULL,
    ticket_id integer NOT NULL,
    sold_at timestamp with time zone NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (ticket_id),
    CONSTRAINT "tickets ref" FOREIGN KEY (ticket_id)
        REFERENCES public.tickets (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT "users ref" FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS public.purchases
    OWNER to postgres;
```
- tokens
```
CREATE TABLE public.tokens
(
    id serial NOT NULL,
    token character varying NOT NULL,
    expiration timestamp with time zone NOT NULL,
    user_id integer NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT "ref to users" FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT
        NOT VALID
);

ALTER TABLE IF EXISTS public.tokens
    OWNER to postgres;
```

## Структура базы данных
<img src=https://github.com/Juliia228/BuyTickets/blob/main/Architecture%20of%20database.png height="400">
