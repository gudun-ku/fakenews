# Example of Microservice Architecture for News Aggregation and Client Streaming

### Components

1. `newssource` - a model of news source, generates one news item per request
2. `provider` - periodically receives news from the source, transforms it into the required format, generates an identifier, sends it to kafka
3. `news-storage` - consumes news from kafka, saves it to the database in the required format, assigns an internal identifier for filtering to avoid saving re-sent items
4. `front-service` - performs streaming of news directly from kafka on the endpoint `/news/stream`, also allows requesting the re-sending of all news saved in the database via POST request to the endpoint `/news/all`
5. `api-gateway` - example of using spring-cloud-api-gateway for routing and filtering, creating a single entry point while the front-service can be accessed through port 8080 and endpoints `/front/news/stream` and `/front/news/all`

### Additional Information
- in the docker folder there is a `docker-compose.yml` file that allows running a kafka broker and its client - `kafdrop` in docker. After starting, kafdrop is available on port 9000 - `http://localhost:9000`
- a Postman request collection is also available there


# Пример микросервисной архитектуры для агрегации новостей и стриминга клиенту

### компоненты

1. `newssource` - модель источника новостей, генерирует одну новость на запрос
2. `provider` - получает периодически новости от источника, преобразует в нужный формат, генерирует идентификатор, отправляет в kafka
3. `news-storage` - потребляет новости из kafka, сохраняет в базу данных
в нужном формате, присваивает внутренний идентификатор, для фильтрации, чтобы не сохранять переотправленные
4. `front-service` - выполняет стриминг новостей непосредственно из kafka,
на ендпойнте `/news/stream`, также позволяет запросить переотправку всех сохраненных в базе новостей POST запросом на ендпойнт `/news/all`
5. `api-gateway` - пример использования spirng-cloud-api-gateway для роутинга и фильтрации, создания единой точки входа
при этом к front-service можно обратиться через порт 8080 и endpoints
`/front/news/stream` и `/front/news/all`

### дополнительно
- в папке docker лежит файл `docker-compose.yml` позволяющий поднять в докере брокер kafka и клиент для него - `kafdrop`. После запуска kafdrop доступен на порту 9000 - `http://localhost:9000`
- там же доступна коллекция запросов для Postman
