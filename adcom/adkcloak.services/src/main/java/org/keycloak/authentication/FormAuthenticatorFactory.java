package org.keycloak.authentication;

import org.keycloak.provider.ProviderFactory;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface FormAuthenticatorFactory extends ProviderFactory<FormAuthenticator>, ConfigurableAuthenticatorFactory {
}
