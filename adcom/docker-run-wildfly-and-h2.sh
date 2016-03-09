#!/bin/bash

CURRENT_DIR=`cd "."; pwd`

cd $CURRENT_DIR && ./docker-runtime-dir.sh
export DOKCER_RUNTIME=zocker_runtime
export DIST_HOME=$CURRENT_DIR/$DOKCER_RUNTIME


cd $DIST_HOME && ./docker-compose-wildfly-and-h2.sh wildfly-kc-versions.conf