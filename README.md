# Инструкция по запуску
### Для запуска нужны JDK17, Maven, Docker, Git
___

#### Запуск

Клонировать репозиторий
- в терминале из пакета ExternalLibrary выполнить ```mvn clean install```
- в терминале из пакета AuthAppLogAppender выполнить ```mvn clean install```
- в терминале корня проекта выполнить ```docker compose -f .\docker-compose.yml up```
- запустить проект Confirmation
- запустить проект Authentication
___

На порту 8080 
- /register принимает запросом ```POST``` модель 
```json
{
  "username":"username",
  "email":"username@mail.com",
  "password":"password"
}
``` 
- /register принимает запросом ```GET``` в параметре ```t``` токен подтверждения регистрации.
    Этот токен прилетает в консоль сервису Confirmation в формате ```UUID```
- /login принимает запросом ```POST``` модель
```json
{
  "username":"username",
  "password":"password"
}
```
- /app пускает по запросу ```GET``` только авторизованных юзеров. Для авторизации в запросе нужен ```Bearer``` токен,
который можно получить после подтверждения регистрации(см. /register ```GET```) или после логина(см. /login ```POST```)