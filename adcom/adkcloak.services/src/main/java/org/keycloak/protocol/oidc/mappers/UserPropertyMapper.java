package org.keycloak.protocol.oidc.mappers;

import org.keycloak.models.ClientSessionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.protocol.ProtocolMapperUtils;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Mappings UserModel property (the property name of a getter method) to an ID Token claim.  Token claim name can be a full qualified nested object name,
 * i.e. "address.country".  This will create a nested
 * json object within the toke claim.
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class UserPropertyMapper extends AbstractOIDCProtocolMapper implements OIDCAccessTokenMapper, OIDCIDTokenMapper {
    private static final List<ProviderConfigProperty> configProperties = new ArrayList<ProviderConfigProperty>();

    static {
        ProviderConfigProperty property;
        property = new ProviderConfigProperty();
        property.setName(ProtocolMapperUtils.USER_ATTRIBUTE);
        property.setLabel(ProtocolMapperUtils.USER_MODEL_PROPERTY_LABEL);
        property.setType(ProviderConfigProperty.STRING_TYPE);
        property.setHelpText(ProtocolMapperUtils.USER_MODEL_PROPERTY_HELP_TEXT);
        configProperties.add(property);
        OIDCAttributeMapperHelper.addAttributeConfig(configProperties);
    }

    public static final String PROVIDER_ID = "oidc-usermodel-property-mapper";


    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return "User Property";
    }

    @Override
    public String getDisplayCategory() {
        return TOKEN_MAPPER_CATEGORY;
    }

    @Override
    public String getHelpText() {
        return "Map a built in user property to a token claim.";
    }

    @Override
    public AccessToken transformAccessToken(AccessToken token, ProtocolMapperModel mappingModel, KeycloakSession session,
                                            UserSessionModel userSession, ClientSessionModel clientSession) {
        if (!OIDCAttributeMapperHelper.includeInAccessToken(mappingModel)) return token;
        setClaim(token, mappingModel, userSession);

        return token;
    }

    @Override
    public IDToken transformIDToken(IDToken token, ProtocolMapperModel mappingModel, KeycloakSession session, UserSessionModel userSession, ClientSessionModel clientSession) {
        if (!OIDCAttributeMapperHelper.includeInIDToken(mappingModel)) return token;
        setClaim(token, mappingModel, userSession);

        return token;
    }

    protected void setClaim(IDToken token, ProtocolMapperModel mappingModel, UserSessionModel userSession) {
        UserModel user = userSession.getUser();
        String propertyName = mappingModel.getConfig().get(ProtocolMapperUtils.USER_ATTRIBUTE);
        String propertyValue = ProtocolMapperUtils.getUserModelValue(user, propertyName);
        OIDCAttributeMapperHelper.mapClaim(token, mappingModel, propertyValue);
    }

    public static ProtocolMapperModel createClaimMapper(String name,
                                      String userAttribute,
                                      String tokenClaimName, String claimType,
                                      boolean consentRequired, String consentText,
                                      boolean accessToken, boolean idToken) {
        return OIDCAttributeMapperHelper.createClaimMapper(name, userAttribute,
                tokenClaimName, claimType,
                consentRequired, consentText,
                accessToken, idToken,
                PROVIDER_ID);
    }


}
