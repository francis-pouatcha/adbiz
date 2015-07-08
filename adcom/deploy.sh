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

echo 'switching to projet directory'
cd $ADCOM_HOME
echo 'cleanning the projet'
mvn clean install

cp adbase.server/target/adbase.server.war $JBOSS_HOME/standalone/deployments/
cp adcatal.server/target/adcatal.server.war $JBOSS_HOME/standalone/deployments/
cp adstock.server/target/adstock.server.war $JBOSS_HOME/standalone/deployments/
cp adinvtry.server/target/adinvtry.server.war $JBOSS_HOME/standalone/deployments/
cp adprocmt.server/target/adprocmt.server.war $JBOSS_HOME/standalone/deployments/
cp adbnsptnr.server/target/adbnsptnr.server.war $JBOSS_HOME/standalone/deployments/
cp adsales.server/target/adsales.server.war $JBOSS_HOME/standalone/deployments/
cp adcshdwr.server/target/adcshdwr.server.war $JBOSS_HOME/standalone/deployments/
cp adacc.server/target/adacc.server.war $JBOSS_HOME/standalone/deployments/
cp adterm.server/target/adterm.server.war $JBOSS_HOME/standalone/deployments/
cp adaptmt.server/target/adaptmt.server.war $JBOSS_HOME/standalone/deployments/

cp adres.client/target/adres.client.war $JBOSS_HOME/standalone/deployments/
cp adbase.client/target/adbase.client.war $JBOSS_HOME/standalone/deployments/
cp adlogin.client/target/adlogin.client.war $JBOSS_HOME/standalone/deployments/
cp adcatal.client/target/adcatal.client.war $JBOSS_HOME/standalone/deployments/
cp adstock.client/target/adstock.client.war $JBOSS_HOME/standalone/deployments/
cp adinvtry.client/target/adinvtry.client.war $JBOSS_HOME/standalone/deployments/
cp adprocmt.client/target/adprocmt.client.war $JBOSS_HOME/standalone/deployments/
cp adbnsptnr.client/target/adbnsptnr.client.war $JBOSS_HOME/standalone/deployments/
cp adsales.client/target/adsales.client.war $JBOSS_HOME/standalone/deployments/
cp adcshdwr.client/target/adcshdwr.client.war $JBOSS_HOME/standalone/deployments/
cp adacc.client/target/adacc.client.war $JBOSS_HOME/standalone/deployments/
cp adaptmt.client/target/adaptmt.client.war $JBOSS_HOME/standalone/deployments/

