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

export JBOSS_HOME=$TOOLS_HOME/$JBOSS_DIR

cd $1 && export prj=${PWD##*/}
echo 'building ' $prj 
mvn clean install
echo 'deploying ' target/$prj.war
cp target/$prj.war $JBOSS_HOME/standalone/deployments/

