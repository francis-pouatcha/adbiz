package org.keycloak.authentication.requiredactions;

import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.events.EventBuilder;
import org.keycloak.events.EventType;
import org.keycloak.login.LoginFormsProvider;
import org.keycloak.models.ClientSessionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.RequiredCredentialModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.models.utils.CredentialValidation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.services.managers.ClientSessionCode;
import org.keycloak.services.messages.Messages;
import org.keycloak.services.validation.Validation;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class UpdateTotp implements RequiredActionProvider, RequiredActionFactory {
    protected static Logger logger = Logger.getLogger(UpdateTotp.class);
    @Override
    public void evaluateTriggers(RequiredActionContext context) {
    }

    @Override
    public void requiredActionChallenge(RequiredActionContext context) {
        Response challenge = context.form()
                .createResponse(UserModel.RequiredAction.CONFIGURE_TOTP);
        context.challenge(challenge);
    }

    @Override
    public void processAction(RequiredActionContext context) {
        EventBuilder event = context.getEvent();
        event.event(EventType.UPDATE_TOTP);
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        String totp = formData.getFirst("totp");
        String totpSecret = formData.getFirst("totpSecret");

        if (Validation.isBlank(totp)) {
            Response challenge = context.form()
                    .setError(Messages.MISSING_TOTP)
                    .createResponse(UserModel.RequiredAction.CONFIGURE_TOTP);
            context.challenge(challenge);
            return;
        } else if (!CredentialValidation.validOTP(context.getRealm(), totp, totpSecret)) {
            Response challenge = context.form()
                    .setError(Messages.INVALID_TOTP)
                    .createResponse(UserModel.RequiredAction.CONFIGURE_TOTP);
            context.challenge(challenge);
            return;
        }

        UserCredentialModel credentials = new UserCredentialModel();
        credentials.setType(context.getRealm().getOTPPolicy().getType());
        credentials.setValue(totpSecret);
        context.getSession().users().updateCredential(context.getRealm(), context.getUser(), credentials);


        // if type is HOTP, to update counter we execute validation based on supplied token
        UserCredentialModel cred = new UserCredentialModel();
        cred.setType(context.getRealm().getOTPPolicy().getType());
        cred.setValue(totp);
        context.getSession().users().validCredentials(context.getRealm(), context.getUser(), cred);

        context.getUser().setOtpEnabled(true);
        context.success();
    }


    @Override
    public void close() {

    }

    @Override
    public RequiredActionProvider create(KeycloakSession session) {
        return this;
    }

    @Override
    public void init(Config.Scope config) {

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public String getDisplayText() {
        return "Configure Totp";
    }


    @Override
    public String getId() {
        return UserModel.RequiredAction.CONFIGURE_TOTP.name();
    }
}
