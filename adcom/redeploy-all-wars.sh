# Read the build configuration file
if [ "x$BUILD_CONF" = "x" ]; then
    CURRENT_DIR=`cd "."; pwd`
    BUILD_CONF="$CURRENT_DIR/build.conf"
	APP_LIST_CONF="$CURRENT_DIR/apps-list.conf"
fi
if [ -r "$BUILD_CONF" ]; then
    . "$BUILD_CONF"
fi

while [ ! -e "$CONFIG_DONE" ]
do
   	echo "             Checking for config to end."
   	sleep 10s	
done

echo "running file $APP_LIST_CONF"

if [ -r "$APP_LIST_CONF" ]; then
    . "$APP_LIST_CONF"
fi

echo "             Apps redeployed succeffuly"
