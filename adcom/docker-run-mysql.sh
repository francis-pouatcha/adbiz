#!/bin/bash

CURRENT_DIR=`cd "."; pwd`

export DOKCER_RUNTIME=zocker_runtime
export DIST_HOME=$CURRENT_DIR/$DOKCER_RUNTIME

cd $CURRENT_DIR && ./setup-docker-runtime-dir.sh

cd $DIST_HOME && ./docker-compose-db2.sh  wildfly-kc-versions.conf