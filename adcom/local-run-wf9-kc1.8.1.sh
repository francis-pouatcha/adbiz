#!/bin/bash

CURRENT_DIR=`cd "."; pwd`

export ADCOM_DIST=adcom.configuration/target/adcom.dist

export DIST_HOME=$CURRENT_DIR/$ADCOM_DIST
if [ ! -d "$DIST_HOME" ]; then
	cd $CURRENT_DIR && mvn clean install
fi

. $DIST_HOME/wf9-kc1.8-h2.conf

# Tools dir
# Check TOOLS_HOME
if [ "x$TOOLS_HOME" = "x" ]; then
	export TOOLS_HOME=~/tools
fi

if [ ! -d "$TOOLS_HOME" ]; then
	echo "Creating dir ~/tools"
    cd && mkdir tools
fi

# Kill existing JBOSS
export RUNNING_JBOSS_PID="$( ps aux | grep '[j]boss' | awk '{print $2}')"

if [ "x$RUNNING_JBOSS_PID" != "x" ]; then
	echo "             Stopping jboss smoothly PID $RUNNING_JBOSS_PID"
	kill -9 $RUNNING_JBOSS_PID > /dev/null
   	sleep 2s	
fi
export RUNNING_JBOSS_PID="$( ps aux | grep '[j]boss' | awk '{print $2}')"
if [ "x$RUNNING_JBOSS_PID" = "x" ]; then
   	echo "             Running jboss instance terminated."
fi


export JBOSS_DIST=$TOOLS_HOME/wildfly-$WILDFLY_VERSION.tar.gz
if [ ! -e "$JBOSS_DIST" ]; then
   	echo " JBOSS_DIST: $JBOSS_DIST does not exist or is not readable dowloading dist"
   	cd $TOOLS_HOME && curl -O https://download.jboss.org/wildfly/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz	
else
	echo " JBOSS_DIST: $JBOSS_DIST"
fi

export JBOSS_HOME=$TOOLS_HOME/wildfly-$WILDFLY_VERSION

if [ -d "$JBOSS_HOME" ]; then
  	cd $TOOLS_HOME && rm -rf $JBOSS_HOME
   	sleep 1s	
fi

cd $TOOLS_HOME && tar xzf $JBOSS_DIST

if [ ! -d "$JBOSS_HOME" ]; then
   	echo "  ERROR:  could not untar $JBOSS_DIST"
   	sleep 1s	
   	exit
fi


cd $JBOSS_HOME && tar xzf $DIST_HOME/adcom.deploy.tar.gz

cp -r $DIST_HOME/appdata $JBOSS_HOME/standalone/appdata



export H2_CONSOLE_WAR=$TOOLS_HOME/h2console.war
if [ -e "$H2_CONSOLE_WAR" ]; then
   	echo " Deploying h2Console"
	cp $H2_CONSOLE_WAR $JBOSS_HOME/standalone/deployments
else
	echo "  File $H2_CONSOLE_WAR not available, H2Console will not be deployed."
fi

echo "             Starting jboss"
cd $JBOSS_HOME && bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 --debug --server-config=standalone-keycloak-$DB.xml --properties=$DIST_HOME/adcom-env-localhost.properties
