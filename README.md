# BuyTickets
REST API для предоставления возможностей пользователям покупать транспортные билеты

## Информация о проекте
Сервер реализован с помощью `Java, Spring Boot, Spring Security, PostgreSQL, lombok`.

Исходный код находится [тут](https://github.com/Juliia228/BuyTickets/tree/main/src/main/java/test/task/stm/BuyTickets). 

В проект добавлено [тестирование](https://github.com/Juliia228/BuyTickets/tree/main/src/test/java/test/task/stm/BuyTickets). Помимо этого, тестирование осуществлялось с помощью Postman и Swagger UI.

Запуск сервера происходит из файла [BuyTicketsApplication](https://github.com/Juliia228/BuyTickets/blob/main/src/main/java/test/task/stm/BuyTickets/BuyTicketsApplication.java). Сервер запускается локально, порт `9090`.
Сборка - Maven

## Выполненные задачи
1) Реализован микросервис с REST интерфейсом, который обрабатывает заявки на покупку билетов.
2) Созданы основные сущности и реализовано взаимодействие с ними:
[Билет](https://github.com/Juliia228/BuyTickets/blob/main/src/main/java/test/task/stm/BuyTickets/models/Ticket.java),
[Маршрут](https://github.com/Juliia228/BuyTickets/blob/main/src/main/java/test/task/stm/BuyTickets/models/Route.java),
[Перевозчик](https://github.com/Juliia228/BuyTickets/blob/main/src/main/java/test/task/stm/BuyTickets/models/Transporter.java),
[Пользователь](https://github.com/Juliia228/BuyTickets/blob/main/src/main/java/test/task/stm/BuyTickets/models/User.java).
3) Основные REST методы:

Регистрация нового пользователя - [register](https://github.com/Juliia228/BuyTickets/blob/main/src/main/java/test/task/stm/BuyTickets/controllers/UserController.java)

Получение списка всех доступных для покупки билетов, с возможностью пагинации и фильтрации - [getTicketsByParams](https://github.com/Juliia228/BuyTickets/blob/main/src/main/java/test/task/stm/BuyTickets/controllers/TicketController.java)

Покупка определенного билета - [buyTicket](https://github.com/Juliia228/BuyTickets/blob/main/src/main/java/test/task/stm/BuyTickets/controllers/PurchaseController.java)

Получение списка всех купленных билетов для текущего пользователя - [getBoughtTickets](https://github.com/Juliia228/BuyTickets/blob/main/src/main/java/test/task/stm/BuyTickets/controllers/TicketController.java)

4) Все данные хранятся в СУБД PostgreSQL.
5) Для доступа к бд использовался JdbcTemplate.
6) Входные данные REST методов валидируются.
7) Присутсвует Swagger документация.
8) **Дополнительная задача 1:**
Реализована аутентификация пользователей с помощью JWT токенов (access и refresh токены).
Добавлены методы для аутентификации уже зарегистрированных пользователей и обновления access токена по refresh токену.
9) **Дополнительная задача 2:** Добавлены роли «Покупатель» и «Администратор».

## Структура базы данных
<img src=https://github.com/Juliia228/BuyTickets/blob/main/Architecture%20of%20database.png height="400">
