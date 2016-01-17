package org.adorsys.adkcloak.cache.none;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.keycloak.models.ClientModel;
import org.keycloak.models.CredentialValidationOutput;
import org.keycloak.models.FederatedIdentityModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserFederationProviderModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserProvider;
import org.keycloak.models.cache.CacheUserProvider;

public class NoneCacheUserProvider implements CacheUserProvider {

	private UserProvider delegate;


	public NoneCacheUserProvider(KeycloakSession session) {
		delegate = session.getProvider(UserProvider.class);
	}

	@Override
	public UserProvider getDelegate() {
		return delegate;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public void setEnabled(boolean enabled) {
		// Noop
	}

	@Override
	public void registerUserInvalidation(RealmModel realm, String id) {
		// Noop
	}

	public UserModel addUser(RealmModel realm, String id, String username, boolean addDefaultRoles,
			boolean addDefaultRequiredActions) {
		return delegate.addUser(realm, id, username, addDefaultRoles, addDefaultRequiredActions);
	}

	public UserModel addUser(RealmModel realm, String username) {
		return delegate.addUser(realm, username);
	}

	public boolean removeUser(RealmModel realm, UserModel user) {
		return delegate.removeUser(realm, user);
	}

	public void addFederatedIdentity(RealmModel realm, UserModel user, FederatedIdentityModel socialLink) {
		delegate.addFederatedIdentity(realm, user, socialLink);
	}

	public boolean removeFederatedIdentity(RealmModel realm, UserModel user, String socialProvider) {
		return delegate.removeFederatedIdentity(realm, user, socialProvider);
	}

	public void updateFederatedIdentity(RealmModel realm, UserModel federatedUser,
			FederatedIdentityModel federatedIdentityModel) {
		delegate.updateFederatedIdentity(realm, federatedUser, federatedIdentityModel);
	}

	public UserModel getUserById(String id, RealmModel realm) {
		return delegate.getUserById(id, realm);
	}

	public UserModel getUserByUsername(String username, RealmModel realm) {
		return delegate.getUserByUsername(username, realm);
	}

	public UserModel getUserByEmail(String email, RealmModel realm) {
		return delegate.getUserByEmail(email, realm);
	}

	public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group, int firstResult, int maxResults) {
		return delegate.getGroupMembers(realm, group, firstResult, maxResults);
	}

	public UserModel getUserByFederatedIdentity(FederatedIdentityModel socialLink, RealmModel realm) {
		return delegate.getUserByFederatedIdentity(socialLink, realm);
	}

	public UserModel getUserByServiceAccountClient(ClientModel client) {
		return delegate.getUserByServiceAccountClient(client);
	}

	public List<UserModel> getUsers(RealmModel realm, boolean includeServiceAccounts) {
		return delegate.getUsers(realm, includeServiceAccounts);
	}

	public int getUsersCount(RealmModel realm) {
		return delegate.getUsersCount(realm);
	}

	public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group) {
		return delegate.getGroupMembers(realm, group);
	}

	public List<UserModel> getUsers(RealmModel realm, int firstResult, int maxResults, boolean includeServiceAccounts) {
		return delegate.getUsers(realm, firstResult, maxResults, includeServiceAccounts);
	}

	public List<UserModel> searchForUser(String search, RealmModel realm) {
		return delegate.searchForUser(search, realm);
	}

	public List<UserModel> searchForUser(String search, RealmModel realm, int firstResult, int maxResults) {
		return delegate.searchForUser(search, realm, firstResult, maxResults);
	}

	public List<UserModel> searchForUserByAttributes(Map<String, String> attributes, RealmModel realm) {
		return delegate.searchForUserByAttributes(attributes, realm);
	}

	public List<UserModel> searchForUserByAttributes(Map<String, String> attributes, RealmModel realm, int firstResult,
			int maxResults) {
		return delegate.searchForUserByAttributes(attributes, realm, firstResult, maxResults);
	}

	public List<UserModel> searchForUserByUserAttribute(String attrName, String attrValue, RealmModel realm) {
		return delegate.searchForUserByUserAttribute(attrName, attrValue, realm);
	}

	public Set<FederatedIdentityModel> getFederatedIdentities(UserModel user, RealmModel realm) {
		return delegate.getFederatedIdentities(user, realm);
	}

	public FederatedIdentityModel getFederatedIdentity(UserModel user, String socialProvider, RealmModel realm) {
		return delegate.getFederatedIdentity(user, socialProvider, realm);
	}

	public void grantToAllUsers(RealmModel realm, RoleModel role) {
		delegate.grantToAllUsers(realm, role);
	}

	public void preRemove(RealmModel realm) {
		delegate.preRemove(realm);
	}

	public void preRemove(RealmModel realm, UserFederationProviderModel link) {
		delegate.preRemove(realm, link);
	}

	public void preRemove(RealmModel realm, RoleModel role) {
		delegate.preRemove(realm, role);
	}

	public void preRemove(RealmModel realm, GroupModel group) {
		delegate.preRemove(realm, group);
	}

	public void preRemove(RealmModel realm, ClientModel client) {
		delegate.preRemove(realm, client);
	}

	public void preRemove(ProtocolMapperModel protocolMapper) {
		delegate.preRemove(protocolMapper);
	}

	public boolean validCredentials(KeycloakSession session, RealmModel realm, UserModel user,
			List<UserCredentialModel> input) {
		return delegate.validCredentials(session, realm, user, input);
	}

	public boolean validCredentials(KeycloakSession session, RealmModel realm, UserModel user,
			UserCredentialModel... input) {
		return delegate.validCredentials(session, realm, user, input);
	}

	public CredentialValidationOutput validCredentials(KeycloakSession session, RealmModel realm,
			UserCredentialModel... input) {
		return delegate.validCredentials(session, realm, input);
	}

	public void close() {
		delegate.close();
	}
}
