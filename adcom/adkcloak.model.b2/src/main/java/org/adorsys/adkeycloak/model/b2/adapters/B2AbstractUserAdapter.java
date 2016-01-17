package org.adorsys.adkeycloak.model.b2.adapters;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adkeycloak.model.b2.repo.B2AbstractUserStore;
import org.adorsys.adkeycloak.model.b2.repo.GroupMemberStore;
import org.adorsys.adkeycloak.model.b2.repo.UserAttributeStore;
import org.adorsys.adkeycloak.model.b2.repo.UserConsentGrantedProtMapperStore;
import org.adorsys.adkeycloak.model.b2.repo.UserConsentGrantedRoleStore;
import org.adorsys.adkeycloak.model.b2.repo.UserConsentStore;
import org.adorsys.adkeycloak.model.b2.repo.UserCredentialStore;
import org.adorsys.adkeycloak.model.b2.repo.UserRequiredActionStore;
import org.adorsys.adkeycloak.model.b2.repo.UserRoleStore;
import org.adorsys.adkeycloak.model.b2.repo.UserStore;

/**
 * 
 * @author fpo
 *
 * @param <T>
 */
public abstract class B2AbstractUserAdapter<T extends B2Entity>  extends AbstractB2Adapter <T>{

	private B2AbstractUserStore<T> b2Store;

    public B2AbstractUserAdapter(B2AbstractUserStore<T> b2Store) {
    	super(b2Store);
        this.b2Store = b2Store;
    }

	protected UserStore getUserStore() {
		return b2Store.getUserStore();
	}
	
	protected UserConsentStore getUserConsentStore(){
		return b2Store.getUserConsentStore();
	}

	protected UserRoleStore getUserRoleSttore(){
		return b2Store.getUserRoleStore();
	}

	protected UserConsentGrantedProtMapperStore getUserConsentGrantedProtMapperStore() {
		return b2Store.getUserConsentGrantedProtMapperStore();
	}

	protected UserConsentGrantedRoleStore getUserConsentGrantedRoleStore() {
		return b2Store.getUserConsentGrantedRoleStore();
	}
	
	protected UserAttributeStore getUserAttributeStore(){
		return b2Store.getUserAttributeStore();
	}

	public UserRequiredActionStore getUserRequiredActionStore() {
		return b2Store.getUserRequiredActionStore();
	}

	public UserCredentialStore getUserCredentialStore() {
		return b2Store.getUserCredentialStore();
	}
	
	public GroupMemberStore getGroupMemberStore() {
		return b2Store.getGroupMemberStore();
	}
}
