This is the development trunk of the adcom point of sales.

Prerequisites:

assuming you are working with a linux on mac based system


TOOLS_HOME
=========
1- We assume you have a home directory.

We will reference your home directory with $HOME or ~/

1- You will need to create a directory named tools in you $HOME
prompt> cd $HOME
prompt> mkdir tools
If this command fails because it already exists, this is fine.

2- Alternatively, you might define another tools directory. Make sure your standard user has write access to that directory;

DOWNLOADING WILDFLY
===================
Following versions of wildfly and keycloacck are in use:
export JBOSS_DIR=wildfly-9.0.2.Final
export KEYCLOAK_VERSION=1.8.0.CR1

So download these and put them in the #TOOLS_HOME directory.

http://download.jboss.org/wildfly/9.0.2.Final/wildfly-9.0.2.Final.tar.gz
http://www.redhat.com/j/elqNow/elqRedir.htm?ref=http://downloads.jboss.org/keycloak/1.8.0.CR1/keycloak-demo-1.8.0.CR1.tar.gz
http://www.redhat.com/j/elqNow/elqRedir.htm?ref=http://downloads.jboss.org/keycloak/1.8.0.CR1/adapters/keycloak-oidc/keycloak-wildfly-adapter-dist-1.8.0.CR1.tar.gz

ADCOM_HOME
==========
adbiz/adcom : this is the directory in which you ind the first pom file.

> mvn clean install : to compile the whole applicaiton.


>./new-deployment.sh shuts down JBoss and reinstall wildfly from scrach.
########################
# Redeploy the whole system. 
# --> Unpack a new version of wildfly, 
# --> Installs keycloack and extensions,
# --> Deploys the adcom realm including users and clients.
# --> Installs applications war (xxx.client.war and xxx.server.war)
# --> Deploy the initial excel data files.   
#
#############


> redeploy-all-wars.sh : to redeploy all application wars. client and server.

> redeploy-war <project.name> compile and redeploy a single project. For example: >redeploy-war adcatal.client

