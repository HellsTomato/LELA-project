# LELA Frontend (MVP)

Минимальный React-клиент для демонстрации API LELA.

## Основной способ запуска (через Docker)

Запускай из корня проекта:

```bash
docker compose up --build -d
```

Frontend будет доступен на http://localhost:5173.

В docker-режиме Nginx автоматически проксирует запросы /api в backend-контейнер.

## Локальный запуск frontend без Docker

1. Открой терминал в папке frontend.
2. Установи зависимости:

```bash
npm install
```

3. Запусти dev-сервер:

```bash
npm run dev
```

В dev-режиме Vite проксирует /api на http://localhost:8080.
