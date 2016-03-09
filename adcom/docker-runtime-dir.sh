#!/bin/bash

CURRENT_DIR=`cd "."; pwd`

export DOKCER_RUNTIME=zocker_runtime

if [ -d "$DOKCER_RUNTIME" ]; then
  	cd $CURRENT_DIR && rm -rf $DOKCER_RUNTIME/*
   	sleep 1s	
else 
	cd $CURRENT_DIR && mkdir $DOKCER_RUNTIME

fi

export ADCOM_DIST=adcom.configuration/target/adcom.dist

export ADCOM_DIST_FILE=$CURRENT_DIR/adcom.configuration/target/adcom.dist.tar.gz
if [ ! -e "$ADCOM_DIST_FILE" ]; then
	cd $CURRENT_DIR && mvn clean install
fi

export DIST_HOME=$CURRENT_DIR/$DOKCER_RUNTIME

cd $DIST_HOME && tar xzf $ADCOM_DIST_FILE
