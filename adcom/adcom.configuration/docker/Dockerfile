FROM jboss/wildfly:9.0.2.Final

MAINTAINER Francis Pouatcha <fpo@adorsys.com>

# Todo externalize this
# ENV KEYCLOAK_VERSION 1.8.1.Final
# ENV IDP_SERVER wildfly-$WILDFLY_VERSION

# ENV IDP_DEPLOY_DIR adcom.deploy-docker-assembly
# ENV KEYCLOAK_DIR $IDP_DEPLOY_DIR/$IDP_SERVER/keycloak-$KEYCLOAK_VERSION
# ENV JBOSS_MODULES_DIR $JBOSS_HOME/modules/system/layers/base
# ENV JBOSS_CONFIG_DIR $JBOSS_HOME/standalone/configuration
# ENV JBOSS_DATA_DIR $JBOSS_HOME/standalone/data
# ENV JBOSS_DEPLOY_DIR $JBOSS_HOME/standalone/deployments

EXPOSE 8080 8787 9990 9999

RUN rm -rf $JBOSS_CONFIG_DIR/standalone_xml_history

WORKDIR $JBOSS_HOME

COPY adcom.deploy.tar.gz /
RUN cd $JBOSS_HOME && tar xf /adcom.deploy.tar.gz 

# COPY certs/server.truststore $JBOSS_HOME/standalone/certs/server.truststore

# Wildfly admin user.
RUN bin/add-user.sh -g admin -u admin -p admin123!

COPY launch-idp-server /

CMD ["bash", "/launch-idp-server"]



