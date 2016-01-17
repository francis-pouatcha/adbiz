#This script is used to make a full projet deployemnt.
#Please set the $ADCOM_HOME environment variable in your profile.
#as follow

export JBOSS_DIR=wildfly-9.0.2.Final
export KEYCLOAK_VERSION=1.8.0.CR1

unset ADCOM_HOME;
#export $ADCOM_HOME=/path/to/your/adcom
# Setup ADCOM_HOME
RESOLVED_ADCOM_HOME=`cd "."; pwd`
if [ "x$ADCOM_HOME" = "x" ]; then
    # get the full path (without any relative bits)
    ADCOM_HOME=$RESOLVED_ADCOM_HOME
   	echo "             ADCOM_HOME: $ADCOM_HOME"
else
 SANITIZED_ADCOM_HOME=`cd "$ADCOM_HOME"; pwd`
 if [ "$RESOLVED_ADCOM_HOME" != "$SANITIZED_ADCOM_HOME" ]; then
   echo ""
   echo "   WARNING:  ADCOM_HOME may be pointing to a different installation - unpredictable results may occur."
   echo ""
   echo "             ADCOM_HOME: $ADCOM_HOME"
   echo ""
   sleep 2s
 fi
fi
export ADCOM_HOME
# make sure home points to your home directory

# Check TOOLS_HOME
if [ "x$TOOLS_HOME" = "x" ]; then
	export TOOLS_HOME=~/tools
fi

echo ""

if [ ! -d "$TOOLS_HOME" ]; then
   	echo "             ERROR:  TOOL_HOME not define. And default directory ~/tools not existant."
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

export JBOSS_HOME=$TOOLS_HOME/$JBOSS_DIR
export JBOSS_DATA_DIR=$JBOSS_HOME/standalone/data
export JBOSS_DEPLOY_DIR=$JBOSS_HOME/standalone/deployments
export JBOSS_CONFIG_DIR=$JBOSS_HOME/standalone/configuration
export JBOSS_MODULES_HOME=$JBOSS_HOME/modules/system/layers/base

echo "             JBOSS_HOME: $JBOSS_HOME"
echo "             JBOSS_DATA_DIR: $JBOSS_DATA_DIR"


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

# deploy modules
echo "             deploying new artifacts"
cp $ADCOM_HOME/adres.client/target/adres.client.war $JBOSS_HOME/standalone/deployments/
cp $ADCOM_HOME/addashboard.client/target/addashboard.client.war $JBOSS_HOME/standalone/deployments/
cp $ADCOM_HOME/adcatal.client/target/adcatal.client.war $JBOSS_HOME/standalone/deployments/
#cp $ADCOM_HOME/adstock.client/target/adstock.client.war $JBOSS_HOME/standalone/deployments/
#cp $ADCOM_HOME/adinvtry.client/target/adinvtry.client.war $JBOSS_HOME/standalone/deployments/

#cp $ADCOM_HOME/adbnsptnr.client/target/adbnsptnr.client.war $JBOSS_HOME/standalone/deployments/


cp $ADCOM_HOME/adcatal.server/target/adcatal.server.war $JBOSS_HOME/standalone/deployments/
#cp $ADCOM_HOME/adstock.server/target/adstock.server.war $JBOSS_HOME/standalone/deployments/
#cp $ADCOM_HOME/adinvtry.server/target/adinvtry.server.war $JBOSS_HOME/standalone/deployments/
#cp $ADCOM_HOME/adbnsptnr.server/target/adbnsptnr.server.war $JBOSS_HOME/standalone/deployments/


cd $JBOSS_DATA_DIR && mkdir adcom
echo "             Import catalogue data"
cd $JBOSS_DATA_DIR/adcom && mkdir adcatal
cp $ADCOM_HOME/adcom.configuration/all-servers/adcom/adcatal/adcatal.xls $JBOSS_DATA_DIR/adcom/adcatal/


#echo "             Import adinvtry data"
#cd $JBOSS_DATA_DIR/adcom && mkdir adinvtry
#cp $ADCOM_HOME/adcom.configuration/all-servers/adcom/adinvtry/adinvtry.xls $JBOSS_DATA_DIR/adcom/adinvtry/

#echo "             Import adstock data"
#cd $JBOSS_DATA_DIR/adcom && mkdir adstock
#cp $ADCOM_HOME/adcom.configuration/all-servers/adcom/adstock/adstock.xls $JBOSS_DATA_DIR/adcom/adstock/

# echo "             Back to adcom home"
# cd $ADCOM_HOME

echo "             Starting jboss"
cd $JBOSS_HOME && bin/standalone.sh --debug --server-config=standalone-keycloak.xml --properties=$JBOSS_HOME/keycloak-config.properties > /dev/null &

echo "             Deployement complete succeffuly, data base being initilized"
