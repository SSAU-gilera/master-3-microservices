# Разработка микросервисных приложений

#### Лобанков Антон Алексеевич

2 курс 1 полугодие (3 семестр)

# Лабораторные работы

> Если в папке лабораторной работы нет файлов программы или появляются ошибки при запуске, вы всегда можете найти код в конце отчёта.

## Лабораторная работа 1

В процессе выполнения заданий ознакомиться с возможностями Spring Cloud Config Server.

**Задание**

1. Создать Spring Cloud Config Server приложение. Помимо файла с общими настройками описать файл с настройками для user-service и company-service. Файлы с настройками должны содержать: порт, на котором запуститься микросервис, описание микросервиса.
2. Создать настройку для test профайла spring для user-service. Переопределить в ней описание микросервиса c использованием environment файла.
3. Создать два микросервиса: user-service, company-service. Настроить их подключение к Config Server. Создать файл для чтения конфигурации. Создать rest контроллер с get методом, который будет возвращать описание микросервиса из конфигурации. Убедиться, что описание сервисов возвращается корректно.
4. Добавить конфигурацию для запуска user-service с тестовым профайлом. Убедиться, что описание сервиса возвращается корректно.

## Лабораторная работа 2

В процессе выполнения заданий ознакомиться с возможностями Spring Cloud Config Server.

**Задание**

1. Установить Docker. Создать docker-compose.yaml файл, в котором описать необходимые для работы сервисы (postgresql). Добавить настройки подключения к созданной базе данных для user-service и company-service (для каждого микросервиса укажите свою схему данных).
2. Подключить фреймворк для управления миграциями баз данных FlyWay в user-service и company-service. Создайте скрипт для создания таблицы user (имя, логин, пароль, емэйл, признак активации/деактивации, идентификатор компании). Для companyservice создайте скрипт для создания таблицы компаний (название, огрн, описание дейтельности, идентификатор директора).
3. Создайте соотвеnствующие Entity, Dto (для пользователей должно возвращаться название компании, для компаний – ФИО пользователя), Repository, Service для таблиц из задания 2
4. Создайте контроллер в user-service, в котором реализуйте следующие end-points:
  -	Получение списка всех пользователей (информация о компании должна подтягиваться синхронное из company-service)
  -	Активация/деактивация пользователя
  -	Создание пользователя (должна быть синхронная проверка из company-service, на существование компании. Если компании не существует – кидать 404 ошибку с соответствующим сообщением)
  -	Обновление информации о пользователе (только имя, емэйл и компанию, для компании должна быть проверка см. выше )
  -	Проверка существования пользователя по идентификатору (если пользователь существует, но неактивен – тоже кидаем 404 ошибку)
  Создайте контроллер в company-service, в котором реализуйте следующие end-points:
  -	Проверка существования компании по ее идентификатору 
  -	Получение списка всех компаний ( с ФИО директора)
  -	Создание компании (должна быть синхронная проверка из user-service, на существование директора. 
  -	Если директора не существует – кидать 404 ошибку с соответствующим сообщением).

## Лабораторная работа 3

В процессе выполнения заданий подключить службу обнаружения микросервисов и настроить api шлюз.

**Задание**

1. Создать новое Spring Boot приложение – Discovery server. В качестве службы обнаружения микросервисов можно использовать eureka.
2. Зарегистрируйте ваши микросервисы (user и company) в созданном Discovery server.
3. Создайте новое Spring boot приложение – Api Gateway. Зарегистрируйте его в Discovery server
4. Задайте маршруты в Api gateway таким образом, чтобы все запросы, которые идут по адресу …/user/… были перенаправлены в user-service, а все запросы по адресу …/company/… в company-service. Проверьте работоспособность приложения.
5. Поправьте url в своих feign clients, таким образом, чтобы вызов сервисов осуществлялся через api gateway. Проверьте работоспособность приложения.

## Лабораторная работа 4

Получение навыков асинхронного взаимодействия микросервисов. 

**Задание**

1. Добавить в docker-compose файл новый сервис для Kafka.
2. Обновить configuration server, добавив в него новые настройки для kafka.
3. Создать енд-поинт для удаления компании (компания помечается удаленной и перестает искаться в енд-поинте для поиска компаний, физически из БД компания на данном этапе не удаляется). Если компании по id не существует – кидаем 404 ошибку. При удалении компании должно отправляться сообщение в user сервис через Kafka. При получении сообщения в юзер сервисе, ищутся все пользователи, работающие в данной компании, и поле company_id для них сбрасывается в null. После этого отправляется сообщение в company service, по которому компания физически удалятся из БД.
4. Продемонстрировать работу енд-поинта удаления компании. 
