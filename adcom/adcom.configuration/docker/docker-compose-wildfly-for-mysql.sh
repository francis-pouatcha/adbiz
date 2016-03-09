#!/bin/bash

if [ "x$1" != "x" ]; then
. $1
fi

export JBOSS_HOME=/opt/jboss/wildfly

docker-compose -f docker-compose-wildfly-for-mysql.yml kill
docker-compose -f docker-compose-wildfly-for-mysql.yml rm
docker-compose -f docker-compose-wildfly-for-mysql.yml build
docker-compose -f docker-compose-wildfly-for-mysql.yml up
