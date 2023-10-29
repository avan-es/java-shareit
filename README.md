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


</details>

<details>

<summary>ЭНДПОИНТЫ SERVER</summary>


</details>