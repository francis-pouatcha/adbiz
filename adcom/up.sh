
cd $1 && export prj=${PWD##*/}
echo 'building ' $prj 
mvn clean install
echo 'deploying ' target/$prj.war
cp target/$prj.war $JBOSS_HOME/standalone/deployments/

