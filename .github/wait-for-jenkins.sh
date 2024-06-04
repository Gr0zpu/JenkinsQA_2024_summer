#!/bin/bash

# Максимальное время ожидания (в секундах)
MAX_WAIT=300
INTERVAL=10
WAITED=0

echo "Waiting for Jenkins to start..."

while true; do
  # Проверка доступности Jenkins
  RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/login)

  if [ "$RESPONSE" -eq "200" ]; then
    echo "Jenkins is up and running!"
    break
  fi

  # Проверка на превышение максимального времени ожидания
  if [ "$WAITED" -ge "$MAX_WAIT" ]; then
    echo "Jenkins did not start within $MAX_WAIT seconds. Exiting..."
    exit 1
  fi

  # Ожидание перед следующей проверкой
  echo "Waiting for Jenkins"
  sleep $INTERVAL
  WAITED=$((WAITED + INTERVAL))
done