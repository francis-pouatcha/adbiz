#!/bin/bash

CURRENT_DIR=`cd "."; pwd`

cd $CURRENT_DIR && ./local-runtime-dir.sh
export LOCAL_RUNTIME=local_runtime
export DIST_HOME=$CURRENT_DIR/$LOCAL_RUNTIME
cd $DIST_HOME && . wildfly-kc-versions.conf

export DB=h2

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

if [ "x$AUTH_SERVER_URL" = "x" ]; then
   	echo "            Missing env property AUTH_SERVER_URL."
   	sleep 1s	
   	exit
fi
echo "              AUTH_SERVER_URL $AUTH_SERVER_URL"

echo "             Starting jboss"

cd $JBOSS_HOME && bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 --debug --server-config=standalone-no-ssl.xml -P=$DIST_HOME/appdata/docker.run-env.properties -P=$DIST_HOME/appdata/docker.run-dbname-h2.properties
