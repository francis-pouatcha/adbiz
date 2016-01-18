########################
# Redeploy the whole system. 
# --> Unpack a new version of wildfly, 
# --> Installs keycloack and extensions,
# --> Deploys the adcom realm including users and clients.
# --> Installs applications war (xxx.client.war and xxx.server.war)
# --> Deploy the initial excel data files.   
#
#############
# Read the build configuration file
if [ "x$BUILD_CONF" = "x" ]; then
    CURRENT_DIR=`cd "."; pwd`
    BUILD_CONF="$CURRENT_DIR/build.conf"
	APP_LIST_CONF="$CURRENT_DIR/apps-list.conf"
	DATA_LIST_CONF="$CURRENT_DIR/data-list.conf"
fi
if [ -r "$BUILD_CONF" ]; then
    . "$BUILD_CONF"
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

export JBOSS_DIST=$TOOLS_HOME/$JBOSS_DIR.tar.gz
if [ ! -e "$JBOSS_DIST" ]; then
   	echo "   ERROR:  $JBOSS_DIST does not exist or is not readable."
   	sleep 2s	
   	exit
else
	echo "             JBOSS_DIST: $JBOSS_DIST"
fi

export KEYCLOAK_DIST_FILE=keycloak-overlay-$KEYCLOAK_VERSION.tar.gz
if [ ! -e "$TOOLS_HOME/$KEYCLOAK_DIST_FILE" ]; then
   	echo "   ERROR:  $KEYCLOAK_DIST_FILE does not exist in directory $TOOLS_HOME or is not readable."
   	sleep 2s	
   	exit
else
	echo "             KEYCLOAK_DIST_FILE: $KEYCLOAK_DIST_FILE"
fi

export KEYCLOAK_ADAPTER_FILE=keycloak-wildfly-adapter-dist-$KEYCLOAK_VERSION.tar.gz
if [ ! -e "$TOOLS_HOME/$KEYCLOAK_ADAPTER_FILE" ]; then
   	echo "   ERROR:  $KEYCLOAK_ADAPTER_FILE does not exist in directory $TOOLS_HOME or is not readable."
   	sleep 2s	
   	exit
else
	echo "             KEYCLOAK_ADAPTER_FILE: $KEYCLOAK_ADAPTER_FILE"
fi


if [ -d "$JBOSS_HOME" ]; then
  	cd $TOOLS_HOME && rm -rf $JBOSS_DIR
   	sleep 1s	
fi

cd $TOOLS_HOME && tar xzf $JBOSS_DIST

if [ ! -d "$JBOSS_HOME" ]; then
   	echo "             ERROR:  could not untar $JBOSS_DIST"
   	sleep 1s	
   	exit
fi

cd $JBOSS_HOME && tar xzf $TOOLS_HOME/$KEYCLOAK_DIST_FILE
cd $JBOSS_HOME && tar xzf $TOOLS_HOME/$KEYCLOAK_ADAPTER_FILE

# deploy adcore.xls
# cd $ADCOM_HOME/adcore.xls && mvn clean install
cd $ADCOM_HOME/adcore.xls/target/adcore.xls && cp -r org $JBOSS_MODULES_HOME/

# deploy adcore.b2
# cd $ADCOM_HOME/adcore.b2 && mvn clean install
cd $ADCOM_HOME/adcore.b2/target/adcore.b2 && cp -r org $JBOSS_MODULES_HOME/

# Override Keycloak JPA Connection Implementation.
# cd $ADCOM_HOME/adkcloak.connections.b2 && mvn clean install
cd $ADCOM_HOME/adkcloak.connections.b2/target/adkcloak.connections.b2 && cp -r org $JBOSS_MODULES_HOME/

# Override invalidation cache
# cd $ADCOM_HOME/adkcloak.cache.none && mvn clean install
cd $ADCOM_HOME/adkcloak.cache.none/target/adkcloak.cache.none && cp -r org $JBOSS_MODULES_HOME/

# Override Keycloak JPA Implementation.
# cd $ADCOM_HOME/adkcloak.model.b2 && mvn clean install
cd $ADCOM_HOME/adkcloak.model.b2/target/adkcloak.model.b2 && cp -r org $JBOSS_MODULES_HOME/

# Override Keycloak Event B2 Implementation.
# cd $ADCOM_HOME/adkcloak.events.b2 && mvn clean install
cd $ADCOM_HOME/adkcloak.events.b2/target/adkcloak.events.b2 && cp -r org $JBOSS_MODULES_HOME/

# deploy adkcloak.services
# cd $ADCOM_HOME/adkcloak.services && mvn clean install
cd $ADCOM_HOME/adkcloak.services/target/adkcloak.services && cp -r org $JBOSS_MODULES_HOME/

cd $JBOSS_HOME/standalone && mkdir data
cp $ADCOM_HOME/adcom.configuration/all-servers/adcom/adkcloak/adkcloak.xls $JBOSS_DATA_DIR/adkcloak.keycloak.xls

cp $ADCOM_HOME/adcom.configuration/$JBOSS_DIR/keycloak-$KEYCLOAK_VERSION/keycloak-server-subsystem/main/server-war/WEB-INF/jboss-deployment-structure.xml $JBOSS_MODULES_HOME/org/keycloak/keycloak-server-subsystem/main/server-war/WEB-INF/
cp $ADCOM_HOME/adcom.configuration/$JBOSS_DIR/keycloak-$KEYCLOAK_VERSION/keycloak-server-subsystem/main/server-war/WEB-INF/web.xml $JBOSS_MODULES_HOME/org/keycloak/keycloak-server-subsystem/main/server-war/WEB-INF/

export H2_CONSOLE_WAR=$TOOLS_HOME/h2console.war
if [ -e "$H2_CONSOLE_WAR" ]; then
   	echo "             Deploying h2Console"
	cp $H2_CONSOLE_WAR $JBOSS_DEPLOY_DIR/
	cd $JBOSS_DEPLOY_DIR && touch h2console.war.dodeploy
	
else
	echo "             File $H2_CONSOLE_WAR H2Console will not be deployed."
fi

cp $ADCOM_HOME/adcom.configuration/$JBOSS_DIR/standalone-keycloak.xml $JBOSS_CONFIG_DIR/standalone-keycloak.xml
cp $ADCOM_HOME/adcom.configuration/$JBOSS_DIR/keycloak-$KEYCLOAK_VERSION/keycloak-server.json $JBOSS_CONFIG_DIR/keycloak-server.json
cp $ADCOM_HOME/adcom.configuration/$JBOSS_DIR/keycloak-$KEYCLOAK_VERSION/keycloak-add-user.json $JBOSS_CONFIG_DIR/keycloak-add-user.json

echo "             Starting jboss"
cd $JBOSS_HOME && bin/standalone.sh --debug --server-config=standalone-keycloak.xml > /dev/null &

export CONFIG_DONE=$JBOSS_HOME/.config.done

while [ ! -e "$CONFIG_DONE" ]
do
   	echo "             Waitingfor for config to end."
   	sleep 10s	
done


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

# deploy application. All applications to be deployed are specified in this file: app-list.conf
if [ -r "$APP_LIST_CONF" ]; then
    . "$APP_LIST_CONF"
fi

# deploy application. All data files to be uploaded are specified in this file: data-list.conf
if [ -r "$DATA_LIST_CONF" ]; then
    . "$DATA_LIST_CONF"
fi

# echo "             Back to adcom home"
# cd $ADCOM_HOME

echo "             Starting jboss"
cd $JBOSS_HOME && bin/standalone.sh --debug --server-config=standalone-keycloak.xml --properties=$JBOSS_HOME/keycloak-config.properties > /dev/null &

echo "             Deployement complete succeffuly, data base being initilized"
