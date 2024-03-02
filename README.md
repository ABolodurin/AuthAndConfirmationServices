# Инструкция по запуску
### Для запуска нужны JDK17, Maven, Docker, Git, Minikube, KubeCTL
___

#### Запуск

Клонировать репозиторий
- в терминале из пакета ExternalLibrary выполнить ```mvn clean install```
- в терминале из пакета AuthAppLogAppender выполнить ```mvn clean install```
- в терминале из пакета Authentication выполнить ```mvn clean package -DDATASOURCE_HOST=localhost -DDB_NAME=authentication_service -DDATASOURCE_USER=postgres
  -DDATASOURCE_PASSWORD=postgres -DDATASOURCE_PORT=5432 -DKAFKA_BOOTSTRAP_SERVER=localhost:29092 -DKAFKA_PORT=29092``` Для успеха нужна "подменная" БД как в параметрах запроса
- в терминале из пакета Authentication выполнить```docker build -t authentication-service:latest .```
- в терминале из пакета Confirmation выполнить ```mvn clean package``` и ```docker build -t confirmation-service:latest .```
- в терминале корня проекта выполнить 
  - ```minikube start```
  - ```minikube image load authentication-service:latest```
  - ```minikube image load confirmation-service:latest```
  - ```kubectl apply -f config-map.yml```
  - ```kubectl apply -f secret-map.yml```
  - ```kubectl apply -f pre-deploy.yml```
  - ```kubectl apply -f db-deploy.yml```
  - ```kubectl apply -f kafka-deploy.yml```
  - ```kubectl apply -f service-deploy.yml```
  - ```minikube service authentication-service``` 
___

На выданном после последней команды адресе 
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