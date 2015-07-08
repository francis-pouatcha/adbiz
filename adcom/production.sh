#This script is used to make a full projet deployemnt.
#Please set the $ADCOM_HOME environment variable in your profile.
#as follow



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

echo 'remove old war'
cd production/ && rm -rf *

echo 'switching to projet directory'
cd $ADCOM_HOME
echo 'cleanning the projet'
mvn clean install -DskipTests

echo 'deploying new artifacts'
cp adbase.server/target/adbase.server.war production/
cp adcatal.server/target/adcatal.server.war production/
cp adstock.server/target/adstock.server.war production/
cp adprocmt.server/target/adprocmt.server.war production/
cp adinvtry.server/target/adinvtry.server.war production/
cp adbnsptnr.server/target/adbnsptnr.server.war production/
cp adsales.server/target/adsales.server.war production/
cp adcshdwr.server/target/adcshdwr.server.war production/
cp adacc.server/target/adacc.server.war production/
cp adterm.server/target/adterm.server.war production/
cp adaptmt.server/target/adaptmt.server.war production/

cp adres.client/target/adres.client.war production/
cp adbase.client/target/adbase.client.war production/
cp adcatal.client/target/adcatal.client.war production/
cp adstock.client/target/adstock.client.war production/
cp adprocmt.client/target/adprocmt.client.war production/
cp adinvtry.client/target/adinvtry.client.war production/
cp adbnsptnr.client/target/adbnsptnr.client.war production/
cp adsales.client/target/adsales.client.war production/
cp adcshdwr.client/target/adcshdwr.client.war production/
cp adacc.client/target/adacc.client.war production/
cp adlogin.client/target/adlogin.client.war production/
cp adaptmt.client/target/adaptmt.client.war production/
cp admanager.client/target/admanager.client.war production/


echo 'building war complete succeffuly, please copy xls file manually'

echo 'script exit.'
