package org.adorsys.adkeycloak.model.b2.repo;

import javax.persistence.EntityManager;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adcore.be.repo.B2AbstractStore;
import org.adorsys.adkeycloak.model.b2.adapters.B2UserProvider;

public abstract class B2AbstractUserStore<T extends B2Entity> extends B2AbstractStore<T> {

    private B2UserProvider userProvider;
	
	public B2AbstractUserStore(B2UserProvider userProvider, EntityManager em, int cacheSize) {
		super(em, cacheSize);
		this.userProvider = userProvider;
	}

	public B2UserProvider getUserProvider() {
		return userProvider;
	}

	public UserStore getUserStore() {
		return userProvider.getUserStore();
	}
	
	public UserConsentStore getUserConsentStore(){
		return userProvider.getUserConsentStore();
	}
	
	public UserRoleStore geUserRoleSttore(){
		return userProvider.getUserRoleStore();
	}
	public UserAttributeStore geUserAttributeStore(){
		return userProvider.getUserAttributeStore();
	}

	@Override
	protected void removeStrict(String entType, String id) {
		userProvider.removeStrict(entType, id);
	}
	
	public UserConsentGrantedProtMapperStore getUserConsentGrantedProtMapperStore() {
		return userProvider.getUserConsentGrantedProtMapperStore();
	}

	public UserConsentGrantedRoleStore getUserConsentGrantedRoleStore() {
		return userProvider.getUserConsentGrantedRoleStore();
	}
	
	public UserAttributeStore getUserAttributeStore(){
		return userProvider.getUserAttributeStore();
	}

	public UserRoleStore getUserRoleStore() {
		return userProvider.getUserRoleStore();
	}

	public UserRequiredActionStore getUserRequiredActionStore() {
		return userProvider.getUserRequiredActionStore();
	}

	public UserCredentialStore getUserCredentialStore() {
		return userProvider.getUserCredentialStore();
	}
	public GroupMemberStore getGroupMemberStore() {
		return userProvider.getGroupMemberStore();
	}
}
