#This script is used to make a full projet deployemnt.
#Please set the $ADCOM_HOME environment variable in your profile.
#as follow

unset ADCOM_HOME;

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

echo 'deleting deployemnt db'
cd && rm -rf $HOME/addb.*

#echo 'deleting keycloak db'
#cd && rm -rf $JBOSS_HOME/standalone/data/keycloak.*

echo 'stopping jboss smoothly'
kill -9 $( ps aux | grep '[j]boss' | awk '{print $2}')

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


echo 'configuring keycloak for OAuth'
cp -r $ADCOM_HOME/adcom.configuration/keycloak/deployments $JBOSS_HOME/standalone
cp -r $ADCOM_HOME/adcom.configuration/keycloak/configuration $JBOSS_HOME/standalone
cp $ADCOM_HOME/adcom.configuration/keycloak/adcomrealm.json $JBOSS_HOME/
cd $JBOSS_HOME
unzip -o $ADCOM_HOME/adcom.configuration/keycloak/adapters/keycloak-eap6-adapter-dist-1.0.4.Final.zip
cd $JBOSS_HOME/standalone/deployments/
unzip -o auth-server.war.zip


echo 'cnfiguring jboss'
cp $ADCOM_HOME/adcom.configuration/jboss-eap-6.3/standalone/configuration/standalone.xml $JBOSS_HOME/standalone/configuration/standalone.xml
# cp $ADCOM_HOME/adcom.configuration/jboss-eap-6.3/standalone/security/adcom.jks $JBOSS_HOME/standalone/security/adcom.jks

echo 'gulp build adcatal'
cd $ADCOM_HOME/adcatal.client/src/main/webapp && npm install
cd $ADCOM_HOME/adcatal.client/src/main/webapp && gulp build

echo 'gulp build addashboard'
cd $ADCOM_HOME/addashboard.client/src/main/webapp && npm install
cd $ADCOM_HOME/addashboard.client/src/main/webapp && gulp build


echo 'starting jboss'
cd $JBOSS_HOME
cd $JBOSS_HOME && bin/standalone.sh -Dkeycloak.migration.action=import -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.strategy=OVERWRITE_EXISTING -Dkeycloak.migration.file=$JBOSS_HOME/adcomrealm.json >/dev/null &
	 		
echo 'switching to projet directory'
cd $ADCOM_HOME
echo 'cleanning the projet'
mvn clean install -DskipTests

echo 'deploying new artifacts'
# cp adbase.server/target/adbase.server.war $JBOSS_HOME/standalone/deployments/
 cp adcatal.server/target/adcatal.server.war $JBOSS_HOME/standalone/deployments/
 cp adstock.server/target/adstock.server.war $JBOSS_HOME/standalone/deployments/
# cp adprocmt.server/target/adprocmt.server.war $JBOSS_HOME/standalone/deployments/
 cp adinvtry.server/target/adinvtry.server.war $JBOSS_HOME/standalone/deployments/
# cp adbnsptnr.server/target/adbnsptnr.server.war $JBOSS_HOME/standalone/deployments/
# cp adsales.server/target/adsales.server.war $JBOSS_HOME/standalone/deployments/
# cp adcshdwr.server/target/adcshdwr.server.war $JBOSS_HOME/standalone/deployments/
# cp adacc.server/target/adacc.server.war $JBOSS_HOME/standalone/deployments/
# cp adterm.server/target/adterm.server.war $JBOSS_HOME/standalone/deployments/
# cp adaptmt.server/target/adaptmt.server.war $JBOSS_HOME/standalone/deployments/

# cp adres.client/target/adres.client.war $JBOSS_HOME/standalone/deployments/
# cp adbase.client/target/adbase.client.war $JBOSS_HOME/standalone/deployments/
cp adcatal.client/target/adcatal.client.war $JBOSS_HOME/standalone/deployments/
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
cp addashboard.client/target/addashboard.client.war $JBOSS_HOME/standalone/deployments/

echo 'copying the .xls file'
cp adcom.configuration/jboss-eap-6.3/adcom/adbase/data/adbase.xls $JBOSS_HOME/adcom/adbase/data/
#cp adcom.configuration/jboss-eap-6.3/adcom/adcatal/data/adcatal.xls $JBOSS_HOME/adcom/adcatal/data/
cp adcom.configuration/jboss-eap-6.3/adcom/adstock/data/adstock.xls $JBOSS_HOME/adcom/adstock/data/
cp adcom.configuration/jboss-eap-6.3/adcom/adbnsptnr/data/adbnsptnr.xls $JBOSS_HOME/adcom/adbnsptnr/data/
cp adcom.configuration/jboss-eap-6.3/adcom/adprocmt/data/adprocmt.xls $JBOSS_HOME/adcom/adprocmt/data/
cp adcom.configuration/jboss-eap-6.3/adcom/adinvtry/data/adinvtry.xls $JBOSS_HOME/adcom/adinvtry/data/
cp adcom.configuration/jboss-eap-6.3/adcom/adsales/data/adsales* $JBOSS_HOME/adcom/adsales/data/

echo 'back to adcom home'
cd $ADCOM_HOME

echo 'deployement complete succeffuly, data base being initilized'

echo 'script exit.'
