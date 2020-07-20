#!/bin/bash
set -a
[ -f .env ] && . .env
set +a

mvn clean package \
     -DDATABASE_URL=${DATABASE_URL} \
     -DDATABASE_USER=${DATABASE_USER} \
     -DDATABASE_PASSWORD=${DATABASE_PASSWORD} && \
java -DDATABASE_URL=${DATABASE_URL} \
     -DDATABASE_USER=${DATABASE_USER} \
     -DDATABASE_PASSWORD=${DATABASE_PASSWORD} \
     -jar "$(pwd)"/target/restaurant-1.0.0.RELEASE.jar