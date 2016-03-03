#!/bin/bash

if [ "x$1" != "x" ]; then
. $1
fi


if [ "x$IDP_SERVER" = "x" ]; then
   	echo "   ERROR:  IDP_SERVER env varialble not defined. Run one of the xxx.conf scripts first. Will exist."
   	sleep 2s	
   	exit
fi

if [ "x$KEYCLOAK_VERSION" = "x" ]; then
   	echo "   ERROR:  KEYCLOAK_VERSION env varialble not defined. Run one of the xxx.conf scripts first. Will exist."
   	sleep 2s	
   	exit
fi

if [ "x$DB" = "x" ]; then
   	echo "   ERROR:  DB env varialble not defined. Run one of the xxx.conf scripts first. Will exist."
   	sleep 2s	
   	exit
fi

# mvn clean install

docker-compose -f ${IDP_SERVER}/keycloak-${KEYCLOAK_VERSION}/docker-compose-h2.yml kill
docker-compose -f ${IDP_SERVER}/keycloak-${KEYCLOAK_VERSION}/docker-compose-h2.yml rm
docker-compose -f ${IDP_SERVER}/keycloak-${KEYCLOAK_VERSION}/docker-compose-h2.yml build
docker-compose -f ${IDP_SERVER}/keycloak-${KEYCLOAK_VERSION}/docker-compose-h2.yml up
