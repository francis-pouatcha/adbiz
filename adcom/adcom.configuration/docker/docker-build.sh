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

if [ "x$ADCOM_DIST" = "x" ]; then
   	echo "   ERROR:  ADCOM_DIST env varialble not defined. Set ADCOM_DIST to directory where you run the docker-compose file. Will exist."
   	sleep 2s	
   	exit
fi

# mvn clean install

docker build -t adcom:0.1

