#!/bin/bash

CURRENT_DIR=`cd "."; pwd`

# source the given configuration file
if [ "x$1" != "x" ]; then
. $1
fi

export ADCOM_DIST=adcom.configuration/target/adcom.dist

export DIST_HOME=$CURRENT_DIR/$ADCOM_DIST
if [ ! -d "$DIST_HOME" ]; then
	cd $CURRENT_DIR && mvn clean install
fi

cd $DIST_HOME && ./docker-compose-h2.sh wf9-kc1.8-h2.conf