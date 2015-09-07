#This script is used to make a full projet deployemnt.
#Please set the $ADCOM_HOME environment variable in your profile.
#as follow

unset ADCOM_HOME;

# Check TOOLS_HOME
if [ "x$TOOLS_HOME" = "x" ]; then
	export TOOLS_HOME=~/tools
fi
echo "             TOOLS_HOME: $TOOLS_HOME"

export WF_VERSION=wildfly-9.0.1.Final
export WF_DIST=$WF_VERSION.tar.gz

echo "             WF_DIST: $WF_DIST"

   echo ""
   echo "   WARNING:  JBOSS_HOME is not defined. set the env property JBOSS_HOME before calling this script."
   echo ""
   sleep 2s
	
	exit
else 

   echo ""
   echo "             TOOLS_HOME: $TOOLS_HOME"
	
fi


set WF_DIST=wildfly-9.0.1.Final.tar.gz
set WF_VERSION=wildfly-9.0.1.Final


# Check JBOSS_HOME
if [ "x$JBOSS_HOME" = "x" ]; then

   echo ""
   echo "   WARNING:  JBOSS_HOME is not defined. set the env property JBOSS_HOME before calling this script."
   echo ""
   sleep 2s
	
	exit
else 

   echo ""
   echo "             JBOSS_HOME: $JBOSS_HOME"
	
fi

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

echo 'stopping jboss smoothly'
kill -9 $( ps aux | grep '[j]boss' | awk '{print $2}')

echo 'deleting deployemnt db'
cd && rm -rf $HOME/addb.*

echo 'create Dir for data'
cd $JBOSS_HOME/ && mkdir adcom
cd $JBOSS_HOME/adcom/ && mkdir adbase
cd $JBOSS_HOME/adcom/adbase/ && mkdir data
cd $JBOSS_HOME/adcom/ && mkdir adcatal
cd $JBOSS_HOME/adcom/adcatal/ && mkdir data
cd $JBOSS_HOME/adcom/ && mkdir adstock
cd $JBOSS_HOME/adcom/adstock/ && mkdir data
cd $JBOSS_HOME/adcom/ && mkdir adbnsptnr
cd $JBOSS_HOME/adcom/adbnsptnr/ && mkdir data
cd $JBOSS_HOME/adcom/ && mkdir adprocmt
cd $JBOSS_HOME/adcom/adprocmt/ && mkdir data
cd $JBOSS_HOME/adcom/ && mkdir adinvtry
cd $JBOSS_HOME/adcom/adinvtry/ && mkdir data
cd $JBOSS_HOME/adcom/ && mkdir adsales
cd $JBOSS_HOME/adcom/adsales/ && mkdir data

cd $JBOSS_HOME/standalone/ && mkdir security

echo 'remove old deployments'
cd $JBOSS_HOME/standalone/deployments/ && rm ad*
cd $JBOSS_HOME/adcom/adbase/data/ && rm ad*
cd $JBOSS_HOME/adcom/adcatal/data/ && rm ad*
cd $JBOSS_HOME/adcom/adstock/data/ && rm ad*
cd $JBOSS_HOME/adcom/adbnsptnr/data/ && rm ad*
cd $JBOSS_HOME/adcom/adprocmt/data/ && rm ad*
cd $JBOSS_HOME/adcom/adinvtry/data/ && rm ad*
cd $JBOSS_HOME/adcom/adsales/data/ && rm ad*

echo 'configuring basic auth'
cp -r $ADCOM_HOME/adcom.configuration/$WF_VERSION/standalone/configuration/adcom-roles.properties $JBOSS_HOME/standalone/configuration/adcom-roles.properties
cp -r $ADCOM_HOME/adcom.configuration/$WF_VERSION/standalone/configuration/adcom-users.properties $JBOSS_HOME/standalone/configuration/adcom-users.properties

cp -r $ADCOM_HOME/adcom.configuration/$WF_VERSION/standalone/configuration/standalone-basic.xml $JBOSS_HOME/standalone/configuration/standalone.xml

echo 'starting jboss'
cd $JBOSS_HOME
cd $JBOSS_HOME && bin/standalone.sh >/dev/null &
	 		
echo 'switching to projet directory and building the projet'
cd $ADCOM_HOME && mvn clean install -DskipTests

echo 'deploying new artifacts'
cp $ADCOM_HOME/adcatal.server/target/adcatal.server.war $JBOSS_HOME/standalone/deployments/
# cp $ADCOM_HOME/adstock.server/target/adstock.server.war $JBOSS_HOME/standalone/deployments/
# cp $ADCOM_HOME/adinvtry.server/target/adinvtry.server.war $JBOSS_HOME/standalone/deployments/

# cp adprocmt.server/target/adprocmt.server.war $JBOSS_HOME/standalone/deployments/
# cp adbase.server/target/adbase.server.war $JBOSS_HOME/standalone/deployments/
# cp adbnsptnr.server/target/adbnsptnr.server.war $JBOSS_HOME/standalone/deployments/
# cp adsales.server/target/adsales.server.war $JBOSS_HOME/standalone/deployments/
# cp adcshdwr.server/target/adcshdwr.server.war $JBOSS_HOME/standalone/deployments/
# cp adacc.server/target/adacc.server.war $JBOSS_HOME/standalone/deployments/
# cp adterm.server/target/adterm.server.war $JBOSS_HOME/standalone/deployments/
# cp adaptmt.server/target/adaptmt.server.war $JBOSS_HOME/standalone/deployments/

# cp addashboard.client/target/addashboard.client.war $JBOSS_HOME/standalone/deployments/
# cp adcatal.client/target/adcatal.client.war $JBOSS_HOME/standalone/deployments/

# cp adres.client/target/adres.client.war $JBOSS_HOME/standalone/deployments/
# cp adbase.client/target/adbase.client.war $JBOSS_HOME/standalone/deployments/
# && cp adstock.client/target/adstock.client.war $JBOSS_HOME/standalone/deployments/
# cp adprocmt.client/target/adprocmt.client.war $JBOSS_HOME/standalone/deployments/
# cp adinvtry.client/target/adinvtry.client.war $JBOSS_HOME/standalone/deployments/
# cp adbnsptnr.client/target/adbnsptnr.client.war $JBOSS_HOME/standalone/deployments/
# cp adsales.client/target/adsales.client.war $JBOSS_HOME/standalone/deployments/
# cp adcshdwr.client/target/adcshdwr.client.war $JBOSS_HOME/standalone/deployments/
# cp adacc.client/target/adacc.client.war $JBOSS_HOME/standalone/deployments/
# cp adlogin.client/target/adlogin.client.war $JBOSS_HOME/standalone/deployments/
# cp adaptmt.client/target/adaptmt.client.war $JBOSS_HOME/standalone/deployments/
# cp admanager.client/target/admanager.client.war $JBOSS_HOME/standalone/deployments/

echo 'copying the .xls file'
# cp $ADCOM_HOME/adcom.configuration/all-servers/adcom/adbase/data/adbase.xls $JBOSS_HOME/adcom/adbase/data/
cp $ADCOM_HOME/adcom.configuration/all-servers/adcom/adcatal/data/adcatal.xls $JBOSS_HOME/adcom/adcatal/data/
# cp $ADCOM_HOME/adcom.configuration/all-servers/adcom/adstock/data/adstock.xls $JBOSS_HOME/adcom/adstock/data/
# cp $ADCOM_HOME/adcom.configuration/all-servers/adcom/adbnsptnr/data/adbnsptnr.xls $JBOSS_HOME/adcom/adbnsptnr/data/
# cp $ADCOM_HOME/adcom.configuration/all-servers/adcom/adprocmt/data/adprocmt.xls $JBOSS_HOME/adcom/adprocmt/data/
# cp $ADCOM_HOME/adcom.configuration/all-servers/adcom/adinvtry/data/adinvtry.xls $JBOSS_HOME/adcom/adinvtry/data/
# cp $ADCOM_HOME/adcom.configuration/all-servers/adcom/adsales/data/adsales* $JBOSS_HOME/adcom/adsales/data/

echo 'back to adcom home'
cd $ADCOM_HOME

echo 'deployement complete succeffuly, data base being initilized'

echo 'script exit.'
