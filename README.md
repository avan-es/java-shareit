# ShareIt (Вещь в аренду) ОПИСАНИЕ ПРОЕКТА В РАЗРАБОТКЕ

Многомодульное приложение. Сервис для шеринга вещей. Обеспечивает пользователям, во-первых, возможность рассказать, какими вещами они готовы поделиться, а во-вторых, находить нужную вещь и брать её в аренду на какое-то время.

# Стек технологий

* Java
* Spring Boot
* REST
* Hibernate
* PostgreSQL
* Mockito
* JUnit
* Docker
* Apache Maven
* Lombok
* Postman
* IntelliJ IDEA
* DBeaver

# Структура

Приложение разбито на два — **server** и **gateway**. Они общаются друг с другом через REST. В **gateway** вынесена вся логика валидации входных данных — кроме той, которая требует работы с БД. Приложение **server** содержит всю основную логику работы сервиса.

Запуск приложения настроен через **Docker**. Приложения **server**, **gateway** и **база данных PostgreSQL** запускаются в отдельном Docker-контейнере каждый. Их взаимодействие настроено через Docker Compose.

# Схема БД

# Функционал и эндпоинты

* Добавление вещи
* Редактирование вещи
* Просмотр списка вещей
* Поиск вещи по названию и описанию вещи (в результате только доступные к бронированию вещи)
* Запросы на бронирование вещи
* Подтверждение или отклонение запрос на бронирование
* Возможность оставлять отзывы на вещи, которые пользователь брал 
* Создание запроса вещи, которой нет в БД
* Просмотр запросов вещей


### Дополнительные требования к функционалу

Сервис должен закрывать доступ к вещи, если она уже забронирована. На случай, если нужной вещи на сервисе нет, у пользователей должна быть возможность оставлять запросы. По запросу можно будет добавлять новые вещи для шеринга.



<details>

<summary>ЭНДПОИНТЫ GATEWAY</summary>

<p align="center">
  <table align="center" width="100%">
    <tr>
        <th colspan="2">
          <img width="50" src="https://github.com/avan-es/java-filmorate/assets/83888190/bf414de4-dbba-4f3a-a888-b180fad09728"/><br>UserController
        </th>
      </tr>
    <tr>
      <td>POST /users</td><td>Добавить нового пользователя</td>
    </tr>
    <tr>
      <td>PATCH /users/{userId}</td><td>Обновить информацию о пользователе по его ID</td>
    </tr>
    <tr>
      <td>GET /users/{userId}</td><td>Получить информацию о пользователе по его ID</td>
    </tr>
    <tr>
      <td>GET /users</td><td>Получить информации о всех пользователях</td>
    </tr>
    <tr>
      <td>DELETE /users/{userId}</td><td>Удалить пользователя по его ID</td>
    </tr>
    <tr>
        <th colspan="2">
          <img width="50" src="https://github.com/avan-es/java-filmorate/assets/83888190/44063823-9839-4876-8791-ed41d8b8453f"/><br>ItemController
        </th>
      </tr>
    <tr>
      <td>POST /items</td><td>Добавить новую вещь</td>
    </tr>
    <tr>
      <td>POST /items/{itemId}/comment</td><td>Опубликовать отзыв на вещь</td>
    </tr>
    <tr>
      <td>PATCH /items/{itemId}</td><td>Обновить информацию о вещи по её ID</td>
    </tr>
    <tr>
      <td>GET /items/search</td><td>Поиск вещи по слову в описании и названии</td>
    </tr>
    <tr>
      <td>GET /items/all</td><td>Получить информацию о всех вещах</td>
    </tr>
    <tr>
      <td>GET /items/{itemId}</td><td>Получить информацию о вещи по её ID</td>
    </tr>
    <tr>
      <td>GET /items</td><td>Получить информацию о всех вещах пользователя (владельца)</td>
    </tr>
    <tr>
      <td>DELETE /items/{itemId}</td><td>Удалить вещь по её ID</td>
    </tr>
    <tr>
        <th colspan="2">
          <img width="50" src="https://github.com/avan-es/java-filmorate/assets/83888190/7947d063-025b-4e50-bb1e-e729f19ec18e"/><br>BookingController
        </th>
      </tr>
    <tr>
      <td>POST /bookings</td><td>Создать бронирование вещи</td>
    </tr>
    <tr>
      <td>PATCH /bookings/{bookingId}</td><td>Обработать бронирование владельцем вещи по его ID</td>
    </tr>
    <tr>
      <td>GET /bookings/owner</td><td>Получить информацию о всех бронированиях своих вещей владельцем</td>
    </tr>
    <tr>
      <td>GET /bookings/all</td><td>Получить информацию о всех бронирования в БД</td>
    </tr>
    <tr>
      <td>GET /bookings/{bookingId}</td><td>Получить информацию о бронирование по его ID</td>
    </tr>
    <tr>
      <td>GET /bookings</td><td>Получить информацию о всех бронированиях, сделанных пользователем</td>
    </tr>
    <tr>
        <th colspan="2">
          <img width="50" src="https://github.com/avan-es/java-filmorate/assets/83888190/a8c3e005-c3c8-4052-9f59-03ba594f956f"/><br>ItemRequestController
        </th>
      </tr>
    <tr>
      <td>POST /requests</td><td>Создать новый запрос вещи</td>
    </tr>
    <tr>
      <td>GET /requests/all</td><td>Получить информацию о всех запрошенных вещах</td>
    </tr>
    <tr>
      <td>GET /requests/{requestId}</td><td>Получить информацию о запрошенной вещи по её ID</td>
    </tr>
    <tr>
      <td>GET /requests</td><td>Получить информацию о своих запросах вещей (пользователем)</td>
    </tr>
  </table>
  </p>

</details>

# Запуск приложения

Для запуска приложения потребуется **Java 11, IntelliJ IDEA, Docker, Maven.**

Алгоритм:

1. Склонировать приложение в свой репозиторий или скачать на компьютер;
2. Открыть проект в **IntelliJ IDEA**;
3. Выполнить `mvn clean package`;
4. Собрать контейнеры из файла **docker-compose.yml**

### Тестирование

Для тестирования подготовлен файл коллекции Postman

[ShareIt-Postman-Tests.postman_collection.json](https://github.com/avan-es/java-shareit/blob/main/postman/ShareIt-Postman-Tests.postman_collection.json)

