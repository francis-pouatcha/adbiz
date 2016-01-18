# Read the build configuration file
if [ "x$BUILD_CONF" = "x" ]; then
    DURRENT_DIR=`cd "."; pwd`
    BUILD_CONF="$DURRENT_DIR/build.conf"
fi
if [ -r "$BUILD_CONF" ]; then
    . "$BUILD_CONF"
fi

echo ""

if [ ! -d "$TOOLS_HOME" ]; then
   	echo "             ERROR:  TOOLS_HOME not define. And default directory ~/tools not existant."
   	sleep 2s	
   	exit
else 
	echo "             TOOLS_HOME: $TOOLS_HOME"
fi


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


echo "             Starting jboss"
cd $JBOSS_HOME && bin/standalone.sh --debug --server-config=standalone-keycloak.xml --properties=$JBOSS_HOME/keycloak-config.properties > /dev/null &
