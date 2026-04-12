# LELA MVP: рабочая область

- Frontend: React (Vite)
- Backend: Java + Spring Boot
- База данных: PostgreSQL
- API: REST

## 1) Структура проекта

```text
LELA/
├─ backend/
│  ├─ pom.xml
│  ├─ Dockerfile
│  ├─ .dockerignore
│  └─ src/main/
│     ├─ java/com/lela/backend/
│     │  ├─ LelaBackendApplication.java
│     │  ├─ config/
│     │  │  ├─ DemoDataConfig.java
│     │  │  └─ WebConfig.java
│     │  ├─ controller/
│     │  │  ├─ CourseController.java
│     │  │  └─ LessonController.java
│     │  ├─ dto/
│     │  │  ├─ CompleteLessonRequest.java
│     │  │  ├─ CompleteLessonResponse.java
│     │  │  ├─ CourseResponse.java
│     │  │  ├─ LessonResponse.java
│     │  │  └─ UnitResponse.java
│     │  ├─ entity/
│     │  │  ├─ Course.java
│     │  │  ├─ Lesson.java
│     │  │  ├─ Progress.java
│     │  │  ├─ Reward.java
│     │  │  ├─ Unit.java
│     │  │  └─ User.java
│     │  ├─ repository/
│     │  │  ├─ CourseRepository.java
│     │  │  ├─ LessonRepository.java
│     │  │  ├─ ProgressRepository.java
│     │  │  ├─ RewardRepository.java
│     │  │  ├─ UnitRepository.java
│     │  │  └─ UserRepository.java
│     │  └─ service/
│     │     ├─ CourseService.java
│     │     └─ LessonService.java
│     └─ resources/
│        └─ application.properties
├─ frontend/
│  ├─ package.json
│  ├─ Dockerfile
│  ├─ nginx.conf
│  ├─ .dockerignore
│  ├─ index.html
│  ├─ vite.config.js
│  └─ src/
│     ├─ App.jsx
│     ├─ main.jsx
│     └─ styles.css
├─ docker-compose.yml
└─ README.md
```

## 2) доменная модель

- User
- Course
- Unit
- Lesson
- Progress
- Reward

### Ключевые связи

- Course 1:N Unit
- Unit 1:N Lesson
- User N:M Reward (разблокированные награды)
- Progress связывает User + Lesson (с уникальной парой user_id + lesson_id)

## 3) Обязательные endpoint'ы

- GET /api/courses
- GET /api/courses/{id}/units
- GET /api/lessons/{id}
- POST /api/lessons/{id}/complete

## 4) Логика завершения урока

Внутри LessonService.completeLesson:

1. Проверить, что пользователь и урок существуют.
2. Проверить, не был ли этот урок уже завершен данным пользователем.
3. Сохранить запись о прогрессе.
4. Начислить баллы за урок пользователю.
5. Проверить награды по порогам баллов.
6. Разблокировать все новые доступные награды.
7. Вернуть ответ с полями:
   - pointsEarned
   - totalPoints
   - rewardUnlocked (true/false)
   - unlockedRewards

## 5) Порядок создания файлов (если повторять вручную)

1. Инфраструктура и запуск:
   - docker-compose.yml
   - backend/pom.xml
   - backend/src/main/resources/application.properties
   - backend/src/main/java/com/lela/backend/LelaBackendApplication.java
2. Доменные сущности:
   - User, Course, Unit, Lesson, Progress, Reward
3. Репозитории:
   - UserRepository, CourseRepository, UnitRepository, LessonRepository, ProgressRepository, RewardRepository
4. Слой DTO:
   - CourseResponse, UnitResponse, LessonResponse, CompleteLessonRequest, CompleteLessonResponse
5. Сервисы:
   - CourseService, LessonService
6. Контроллеры:
   - CourseController, LessonController
7. Конфигурация и демо-данные:
   - DemoDataConfig, WebConfig
8. Frontend-клиент для демо:
   - frontend package + App

## 6) Инструкция по запуску через Docker

Проект поднимается целиком одной командой (PostgreSQL + backend + frontend):

```bash
docker compose up --build -d
```

После запуска сервисы доступны по адресам:

- Frontend: http://localhost:5173
- Backend API: http://localhost:8080
- PostgreSQL: localhost:5432

Остановка проекта:

```bash
docker compose down
```

Остановка с удалением тома БД (сброс данных):

```bash
docker compose down -v
```

### Важно про API в Docker

- Frontend в контейнере работает через Nginx.
- Запросы на /api автоматически проксируются в backend-контейнер.
- Поэтому ручная настройка API URL для docker-режима не требуется.

## 7) Локальный запуск без Docker (опционально)

Если нужно запускать по частям локально:

1. Подними только БД:

```bash
docker compose up -d postgres
```

2. Запусти backend из папки backend:

```bash
mvn spring-boot:run
```

3. Запусти frontend из папки frontend:

```bash
npm install
npm run dev
```

Если в Windows видишь ошибку "mvn is not recognized", установи Maven и открой
терминал заново, либо запусти backend из IntelliJ IDEA / VS Code Spring Tools.

## 8) Быстрая проверка API

### GET список курсов

```bash
curl http://localhost:8080/api/courses
```

### GET список юнитов курса 1

```bash
curl http://localhost:8080/api/courses/1/units
```

### GET урок 1

```bash
curl http://localhost:8080/api/lessons/1
```

### POST завершить урок 1 для пользователя 1

```bash
curl -X POST http://localhost:8080/api/lessons/1/complete \
  -H "Content-Type: application/json" \
  -d '{"userId":1}'
```

Ожидаемые поля в ответе:

- message
- userId
- lessonId
- pointsEarned
- totalPoints
- rewardUnlocked
- unlockedRewards
