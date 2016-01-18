###############
# Redeploy a war xxx.client.war or xxx.server.war. Just copy the given war to the $JBOSS_DEPLOY_DIR
#
# Read the build configuration file
################
if [ "x$BUILD_CONF" = "x" ]; then
    CURRENT_DIR=`cd "."; pwd`
    BUILD_CONF="$CURRENT_DIR/build.conf"
fi
if [ -r "$BUILD_CONF" ]; then
    . "$BUILD_CONF"
fi

cd $1 && export prj=${PWD##*/}
echo 'building ' $prj 
mvn clean install
echo 'deploying ' target/$prj.war
cp target/$prj.war $JBOSS_HOME/standalone/deployments/

