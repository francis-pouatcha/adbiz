#!/bin/bash

CURRENT_DIR=`cd "."; pwd`

export DOKCER_RUNTIME=zocker_runtime

if [ -d "$DOKCER_RUNTIME" ]; then
  	cd $CURRENT_DIR && rm -rf $DOKCER_RUNTIME/*
   	sleep 1s	
else 
	cd $CURRENT_DIR && mkdir $DOKCER_RUNTIME

fi

# source the given configuration file
if [ "x$1" != "x" ]; then
. $1
fi

export ADCOM_DIST=$CURRENT_DIR/adcom.configuration/target/adcom.dist.tar.gz

if [ ! -e "$ADCOM_DIST" ]; then
	cd $CURRENT_DIR && mvn clean install
fi

export DIST_HOME=$CURRENT_DIR/$DOKCER_RUNTIME

cd $DIST_HOME && tar xzf $ADCOM_DIST

cd $DIST_HOME && ./docker-compose-h2.sh wf9-kc1.8-h2.conf