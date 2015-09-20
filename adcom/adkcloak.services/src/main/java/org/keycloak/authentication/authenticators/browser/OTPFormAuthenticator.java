package org.keycloak.authentication.authenticators.browser;

import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.events.Errors;
import org.keycloak.login.LoginFormsProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.services.messages.Messages;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class OTPFormAuthenticator extends AbstractUsernameFormAuthenticator implements Authenticator {
    public static final String TOTP_FORM_ACTION = "totp";

    @Override
    public void action(AuthenticationFlowContext context) {
        validateOTP(context);
    }

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        Response challengeResponse = challenge(context, null);
        context.challenge(challengeResponse);
    }

    public void validateOTP(AuthenticationFlowContext context) {
        MultivaluedMap<String, String> inputData = context.getHttpRequest().getDecodedFormParameters();
        List<UserCredentialModel> credentials = new LinkedList<>();
        String password = inputData.getFirst(CredentialRepresentation.TOTP);
        if (password == null) {
            Response challengeResponse = challenge(context, null);
            context.challenge(challengeResponse);
            return;
        }
        credentials.add(UserCredentialModel.otp(context.getRealm().getOTPPolicy().getType(), password));
        boolean valid = context.getSession().users().validCredentials(context.getRealm(), context.getUser(), credentials);
        if (!valid) {
            context.getEvent().user(context.getUser())
                    .error(Errors.INVALID_USER_CREDENTIALS);
            Response challengeResponse = challenge(context, Messages.INVALID_TOTP);
            context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, challengeResponse);
            return;
        }
        context.success();
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    protected Response challenge(AuthenticationFlowContext context, String error) {
        LoginFormsProvider forms = context.form();
        if (error != null) forms.setError(error);

        return forms.createLoginTotp();
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return session.users().configuredForCredentialType(realm.getOTPPolicy().getType(), realm, user);
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        if (!user.getRequiredActions().contains(UserModel.RequiredAction.CONFIGURE_TOTP.name())) {
            user.addRequiredAction(UserModel.RequiredAction.CONFIGURE_TOTP.name());
        }

    }



    @Override
    public void close() {

    }
}
