# Simple-Botanica
Сервис по уходу за домашними растениями
## Цель
1) Разработать сервис “Уход за домашними растениями”.
2) Сервис должен быть доступен в браузере через интернет.
3) Сервис позволяет управлять процессом ухода за домашними растениями, а именно:
* Добавлять новых пользователей
* Выбирать для пользователя растения
* Добавлять для пользователя неизвестные сервису растения
* Вести журнал ухода за выбранными растениями (полив, внос удобрений)
* Вести статистику по уходу за растениями:
* Количество поливов за указанный пользователей промежуток времени
* Количество взносов удобрений за указанный пользователей промежуток времени
## Технологии
###### В проекте используются:
* Angular JS
* MySQL
* Java 17
* Gradle
* Spring Boot 3
* Docker
* Flyway
## Требования для запуска
(на стадии разработки)
### Windows
1) Установить Docker Desktop ( [Сайт для скачивания](https://www.docker.com/products/docker-desktop/) )
2) При требовании при запуске Docker Desktop, перейти по предоставленной ссылке и установить WSL2
3) Проверить установку docker'а с помощью команды в PowerShell. Команда вернет информацию о версии программы, если Docker Desktop был установлен корректно
> docker version
4) Установить Intellij Idea ( [Сайт для скачивания](https://www.jetbrains.com/ru-ru/idea/download/#section=windows) )
###### Далее необходим лишь один из пунктов, расположенных под п.5, в зависимости от Ваших препочтений
* На главной странице проекта ( [Главная страница](https://github.com/AlexeySenkin/Simple-Botanica) ) нажать на зеленую кнопку 'Code' в правом верхнем углу и скопировать HTTPS-ссылку на проект, в открытом окне для выбора проекта Intellij Idea нажать на кнопку 'Get from VCS', в открывшееся окно вставить ссылку в поле 'URL' и выбрать место на локальной машине для установки в окне 'Directory'
* На главной странице проекта ( [Главная страница](https://github.com/AlexeySenkin/Simple-Botanica) ) нажать на зеленую кнопку 'Code' в правом верхнем углу и нажать на кнопку 'Download ZIP', выбрать место для скачивания. Распокавать проект, затем в открытом окне для выбора проекта Intellij Idea нажать на кнопку 'Open' и выбрать распакованную папку проекта
5) В меню Gradle(правая полоска вкладок, кнопка 'Gradle') открыть и выполнить двойным нажатием
> Simple-Botanica -> Tasks -> build -> build
6) В меню навигации(левая полоса вкладок, кнопка 'Project') перейти по любому из указанных ниже адресов
> SimpleBotanica -> module.auth-service -> src -> main -> docker-compose.yml
>
> SimpleBotanica -> module.plant-service -> src -> main -> docker-compose.yml
7) Нажать на двойные зеленые стрелки(при наведении на стрелки Вы увидите подсказку 'docker-compose up')
8) Запустить сервисы (нажать на файл один раз и нажать Ctrl+Shift+F10 или правый клик и выбрать вариант 'Run 'название файла'') в следующем порядке:
> SimpleBotanica -> module.plant-service -> src -> main -> java -> PlantServiceApplication
>
> SimpleBotanica -> module.auth-service -> src -> main -> java -> AuthServiceApplication
>
> SimpleBotanica -> module.user-service -> src -> main -> java -> UserServiceApplication
>
> SimpleBotanica -> module.frontend-service -> src -> main -> java -> FrontendServiceApplication
9) Теперь приложение доступно по [установленному адресу](http://localhost:3010/simplebotanica.ru/index.html#!/)
### Linux
Запустить сервисы (нажать на файл один раз и нажать Ctrl+Shift+F10 или правый клик и выбрать вариант 'Run 'название файла'') в следующем порядке:
> SimpleBotanica -> module.plant-service -> src -> main -> java -> PlantServiceApplication
>
> SimpleBotanica -> module.auth-service -> src -> main -> java -> AuthServiceApplication
>
> SimpleBotanica -> module.user-service -> src -> main -> java -> UserServiceApplication
>
> SimpleBotanica -> module.frontend-service -> src -> main -> java -> FrontendServiceApplication
### IOS
Запустить сервисы (нажать на файл один раз и нажать Ctrl+Shift+F10 или правый клик и выбрать вариант 'Run 'название файла'') в следующем порядке:
> SimpleBotanica -> module.plant-service -> src -> main -> java -> PlantServiceApplication
>
> SimpleBotanica -> module.auth-service -> src -> main -> java -> AuthServiceApplication
>
> SimpleBotanica -> module.user-service -> src -> main -> java -> UserServiceApplication
>
> SimpleBotanica -> module.frontend-service -> src -> main -> java -> FrontendServiceApplication
## Возможности проекта
### Возможности всех пользователей
![Просмотр всех растений](/ExampleMedia/SimpleBotanicaPlantsViewSample.gif)
![Поиск нужных растений](/ExampleMedia/SimpleBotanicaSearchSample.gif)
![Просмотр конкретного растения](/ExampleMedia/SimpleBotanicaPlantViewSample.gif)
![Регистрация на сайте](/ExampleMedia/SimpleBotanicaRegistrationSample.gif)
![Вход в аккаунт на сайте](/ExampleMedia/SimpleBotanicaLogInSample.gif)
### Возможности модератора
![Добавление растения](/ExampleMedia/SimpleBotanicaAddingPlantSample.gif)
![Изменение растения](/ExampleMedia/SimpleBotanicaRedactingPlantSample.gif)
![Удаление растения из общего списка](/ExampleMedia/SimpleBotanicaDeletingPlantSample.gif)
### Возможности зарегистрированного пользователя
![Добавление растения в список своих растений](/ExampleMedia/SimpleBotanicaAddingPlantToUserSample.gif)
![Просмотр своего списка растений](/ExampleMedia/SimpleBotanicaUserPlantsViewSample.gif)
![Просмотр конкретного растения в списке ухода](/ExampleMedia/SimpleBotanicaUserPlantViewSample.gif)
![Добавление информации в журнал ухода](/ExampleMedia/SimpleBotanicaTakingCareViewSample.gif)
![Удаление растения из списка ухода](/ExampleMedia/SimpleBotanicaDeletingFromCareViewSample.gif)
![Выход из аккаунта на сайте](/ExampleMedia/SimpleBotanicaLogOutSample.gif)
![Просмотр своего личного кабинета](/ExampleMedia/SimpleBotanicaUserProfileViewSample.gif)
