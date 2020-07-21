#!/bin/bash

mvn clean package && \
java -jar "$(pwd)"/target/restaurant-1.0.0.RELEASE.jar