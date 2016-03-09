#!/bin/bash

docker-compose -f docker-compose-mysql.yml kill
docker-compose -f docker-compose-mysql.yml rm
docker-compose -f docker-compose-mysql.yml build
docker-compose -f docker-compose-mysql.yml up
