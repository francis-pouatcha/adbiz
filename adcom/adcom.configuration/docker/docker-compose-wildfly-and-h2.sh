#!/bin/bash

if [ "x$1" != "x" ]; then
. $1
fi

export JBOSS_HOME=/opt/jboss/wildfly

docker-compose -f docker-compose-wildfly-and-h2.yml kill
docker-compose -f docker-compose-wildfly-and-h2.yml rm
docker-compose -f docker-compose-wildfly-and-h2.yml build
docker-compose -f docker-compose-wildfly-and-h2.yml up
