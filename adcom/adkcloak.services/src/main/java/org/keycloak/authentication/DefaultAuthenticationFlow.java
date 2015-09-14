package org.keycloak.authentication;

import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.AuthenticationFlowModel;
import org.keycloak.models.ClientSessionModel;
import org.keycloak.models.UserModel;

import javax.ws.rs.core.Response;
import java.util.Iterator;
import java.util.List;

/**
* @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
* @version $Revision: 1 $
*/
public class DefaultAuthenticationFlow implements AuthenticationFlow {
    Response alternativeChallenge = null;
    AuthenticationExecutionModel challengedAlternativeExecution = null;
    boolean alternativeSuccessful = false;
    List<AuthenticationExecutionModel> executions;
    Iterator<AuthenticationExecutionModel> executionIterator;
    AuthenticationProcessor processor;
    AuthenticationFlowModel flow;

    public DefaultAuthenticationFlow(AuthenticationProcessor processor, AuthenticationFlowModel flow) {
        this.processor = processor;
        this.flow = flow;
        this.executions = processor.getRealm().getAuthenticationExecutions(flow.getId());
        this.executionIterator = executions.iterator();
    }

    protected boolean isProcessed(AuthenticationExecutionModel model) {
        if (model.isDisabled()) return true;
        ClientSessionModel.ExecutionStatus status = processor.getClientSession().getExecutionStatus().get(model.getId());
        if (status == null) return false;
        return status == ClientSessionModel.ExecutionStatus.SUCCESS || status == ClientSessionModel.ExecutionStatus.SKIPPED
                || status == ClientSessionModel.ExecutionStatus.ATTEMPTED
                || status == ClientSessionModel.ExecutionStatus.SETUP_REQUIRED;
    }


    @Override
    public Response processAction(String actionExecution) {
        while (executionIterator.hasNext()) {
            AuthenticationExecutionModel model = executionIterator.next();
            if (isProcessed(model)) {
                AuthenticationProcessor.logger.debug("execution is processed");
                if (!alternativeSuccessful && model.isAlternative() && processor.isSuccessful(model))
                    alternativeSuccessful = true;
                continue;
            }
            if (model.isAutheticatorFlow()) {
                AuthenticationFlow authenticationFlow = processor.createFlowExecution(model.getFlowId(), model);
                return authenticationFlow.processAction(actionExecution);
            } else if (model.getId().equals(actionExecution)) {
                AuthenticatorFactory factory = (AuthenticatorFactory) processor.getSession().getKeycloakSessionFactory().getProviderFactory(Authenticator.class, model.getAuthenticator());
                Authenticator authenticator = factory.create();
                AuthenticatorContext result = processor.createAuthenticatorContext(model, authenticator, executions);
                authenticator.action(result);
                Response response = processResult(result);
                if (response == null) return processFlow();
                else return response;
            }
        }
        throw new AuthenticationProcessor.AuthException("action is not in current execution", AuthenticationProcessor.Error.INTERNAL_ERROR);
    }

    @Override
    public Response processFlow() {
        while (executionIterator.hasNext()) {
            AuthenticationExecutionModel model = executionIterator.next();
            if (isProcessed(model)) {
                AuthenticationProcessor.logger.debug("execution is processed");
                if (!alternativeSuccessful && model.isAlternative() && processor.isSuccessful(model))
                    alternativeSuccessful = true;
                continue;
            }
            if (model.isAlternative() && alternativeSuccessful) {
                processor.getClientSession().setExecutionStatus(model.getId(), ClientSessionModel.ExecutionStatus.SKIPPED);
                continue;
            }
            if (model.isAutheticatorFlow()) {
                AuthenticationFlow authenticationFlow = processor.createFlowExecution(model.getFlowId(), model);
                Response flowChallenge = authenticationFlow.processFlow();
                if (flowChallenge == null) {
                    processor.getClientSession().setExecutionStatus(model.getId(), ClientSessionModel.ExecutionStatus.SUCCESS);
                    if (model.isAlternative()) alternativeSuccessful = true;
                    continue;
                } else {
                    if (model.isAlternative()) {
                        alternativeChallenge = flowChallenge;
                        challengedAlternativeExecution = model;
                    } else if (model.isRequired()) {
                        processor.getClientSession().setExecutionStatus(model.getId(), ClientSessionModel.ExecutionStatus.CHALLENGED);
                        return flowChallenge;
                    } else if (model.isOptional()) {
                        processor.getClientSession().setExecutionStatus(model.getId(), ClientSessionModel.ExecutionStatus.SKIPPED);
                        continue;
                    } else {
                        processor.getClientSession().setExecutionStatus(model.getId(), ClientSessionModel.ExecutionStatus.SKIPPED);
                        continue;
                    }
                    return flowChallenge;
                }
            }

            AuthenticatorFactory factory = (AuthenticatorFactory) processor.getSession().getKeycloakSessionFactory().getProviderFactory(Authenticator.class, model.getAuthenticator());
            if (factory == null) {
                throw new AuthenticationProcessor.AuthException("Could not find AuthenticatorFactory for: " + model.getAuthenticator(), AuthenticationProcessor.Error.INTERNAL_ERROR);
            }
            Authenticator authenticator = factory.create();
            AuthenticationProcessor.logger.debugv("authenticator: {0}", factory.getId());
            UserModel authUser = processor.getClientSession().getAuthenticatedUser();

            if (authenticator.requiresUser() && authUser == null) {
                if (alternativeChallenge != null) {
                    processor.getClientSession().setExecutionStatus(challengedAlternativeExecution.getId(), ClientSessionModel.ExecutionStatus.CHALLENGED);
                    return alternativeChallenge;
                }
                throw new AuthenticationProcessor.AuthException("authenticator: " + factory.getId(), AuthenticationProcessor.Error.UNKNOWN_USER);
            }
            boolean configuredFor = false;
            if (authenticator.requiresUser() && authUser != null) {
                configuredFor = authenticator.configuredFor(processor.getSession(), processor.getRealm(), authUser);
                if (!configuredFor) {
                    if (model.isRequired()) {
                        if (model.isUserSetupAllowed()) {
                            AuthenticationProcessor.logger.debugv("authenticator SETUP_REQUIRED: {0}", factory.getId());
                            processor.getClientSession().setExecutionStatus(model.getId(), ClientSessionModel.ExecutionStatus.SETUP_REQUIRED);
                            authenticator.setRequiredActions(processor.getSession(), processor.getRealm(), processor.getClientSession().getAuthenticatedUser());
                            continue;
                        } else {
                            throw new AuthenticationProcessor.AuthException(AuthenticationProcessor.Error.CREDENTIAL_SETUP_REQUIRED);
                        }
                    } else if (model.isOptional()) {
                        processor.getClientSession().setExecutionStatus(model.getId(), ClientSessionModel.ExecutionStatus.SKIPPED);
                        continue;
                    }
                }
            }
            AuthenticatorContext context = processor.createAuthenticatorContext(model, authenticator, executions);
            authenticator.authenticate(context);
            Response response = processResult(context);
            if (response != null) return response;
        }
        return null;
    }


    public Response processResult(AuthenticatorContext result) {
        AuthenticationExecutionModel execution = result.getExecution();
        AuthenticationProcessor.Status status = result.getStatus();
        if (status == AuthenticationProcessor.Status.SUCCESS) {
            AuthenticationProcessor.logger.debugv("authenticator SUCCESS: {0}", execution.getAuthenticator());
            processor.getClientSession().setExecutionStatus(execution.getId(), ClientSessionModel.ExecutionStatus.SUCCESS);
            if (execution.isAlternative()) alternativeSuccessful = true;
            return null;
        } else if (status == AuthenticationProcessor.Status.FAILED) {
            AuthenticationProcessor.logger.debugv("authenticator FAILED: {0}", execution.getAuthenticator());
            processor.logFailure();
            processor.getClientSession().setExecutionStatus(execution.getId(), ClientSessionModel.ExecutionStatus.FAILED);
            if (result.getChallenge() != null) {
                return sendChallenge(result, execution);
            }
            throw new AuthenticationProcessor.AuthException(result.getError());
        } else if (status == AuthenticationProcessor.Status.FORCE_CHALLENGE) {
            processor.getClientSession().setExecutionStatus(execution.getId(), ClientSessionModel.ExecutionStatus.CHALLENGED);
            return sendChallenge(result, execution);
        } else if (status == AuthenticationProcessor.Status.CHALLENGE) {
            AuthenticationProcessor.logger.debugv("authenticator CHALLENGE: {0}", execution.getAuthenticator());
            if (execution.isRequired()) {
                processor.getClientSession().setExecutionStatus(execution.getId(), ClientSessionModel.ExecutionStatus.CHALLENGED);
                return sendChallenge(result, execution);
            }
            UserModel authenticatedUser = processor.getClientSession().getAuthenticatedUser();
            if (execution.isOptional() && authenticatedUser != null && result.getAuthenticator().configuredFor(processor.getSession(), processor.getRealm(), authenticatedUser)) {
                processor.getClientSession().setExecutionStatus(execution.getId(), ClientSessionModel.ExecutionStatus.CHALLENGED);
                return sendChallenge(result, execution);
            }
            if (execution.isAlternative()) {
                alternativeChallenge = result.getChallenge();
                challengedAlternativeExecution = execution;
            } else {
                processor.getClientSession().setExecutionStatus(execution.getId(), ClientSessionModel.ExecutionStatus.SKIPPED);
            }
            return null;
        } else if (status == AuthenticationProcessor.Status.FAILURE_CHALLENGE) {
            AuthenticationProcessor.logger.debugv("authenticator FAILURE_CHALLENGE: {0}", execution.getAuthenticator());
            processor.logFailure();
            processor.getClientSession().setExecutionStatus(execution.getId(), ClientSessionModel.ExecutionStatus.CHALLENGED);
            return sendChallenge(result, execution);
        } else if (status == AuthenticationProcessor.Status.ATTEMPTED) {
            AuthenticationProcessor.logger.debugv("authenticator ATTEMPTED: {0}", execution.getAuthenticator());
            if (execution.getRequirement() == AuthenticationExecutionModel.Requirement.REQUIRED) {
                throw new AuthenticationProcessor.AuthException(AuthenticationProcessor.Error.INVALID_CREDENTIALS);
            }
            processor.getClientSession().setExecutionStatus(execution.getId(), ClientSessionModel.ExecutionStatus.ATTEMPTED);
            return null;
        } else {
            AuthenticationProcessor.logger.debugv("authenticator INTERNAL_ERROR: {0}", execution.getAuthenticator());
            AuthenticationProcessor.logger.error("Unknown result status");
            throw new AuthenticationProcessor.AuthException(AuthenticationProcessor.Error.INTERNAL_ERROR);
        }

    }

    public Response sendChallenge(AuthenticatorContext result, AuthenticationExecutionModel execution) {
        processor.getClientSession().setNote(AuthenticationProcessor.CURRENT_AUTHENTICATION_EXECUTION, execution.getId());
        return result.getChallenge();
    }


}
