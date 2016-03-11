#!/bin/bash

CURRENT_DIR=`cd "."; pwd`


cd $CURRENT_DIR && ./docker-runtime-dir.sh
export DOKCER_RUNTIME=zocker_runtime
export DIST_HOME=$CURRENT_DIR/$DOKCER_RUNTIME

if [ "x$AUTH_SERVER_URL" = "x" ]; then
   	echo "            Missing env property AUTH_SERVER_URL."
   	sleep 1s	
   	exit
fi
echo "              AUTH_SERVER_URL $AUTH_SERVER_URL"

cd $DIST_HOME && ./docker-compose-wildfly-for-db2.sh wildfly-kc-versions.conf