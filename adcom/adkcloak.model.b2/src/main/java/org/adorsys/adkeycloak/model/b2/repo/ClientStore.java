package org.adorsys.adkeycloak.model.b2.repo;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.adorsys.adcore.b2.jpa.NnV;
import org.adorsys.adkeycloak.model.b2.adapters.B2RealmProvider;
import org.adorsys.adkeycloak.model.b2.jpa.B2ClientEntity;
import org.adorsys.adcore.b2.jpa.B2PersContent_;

public class ClientStore extends B2AbstractRealmStore<B2ClientEntity> {

	public ClientStore(B2RealmProvider realmProvider, EntityManager em, int cacheSize) {
		super(realmProvider, em, cacheSize);
	}

	@Override
	protected Class<B2ClientEntity> getEntityClass() {
		return B2ClientEntity.class;
	}
	@Override
	protected String getEntityType() {
		return B2ClientEntity.ENT_TYPE;
	}

	
	public B2ClientEntity findByName(String name){
		if(name==null) return null;
		List<B2ClientEntity> found = find(0, 1, entTypeNV, NnV.inst(B2PersContent_.idx1, name));
		if(found.isEmpty()) return null;
		return found.iterator().next();
	}

	public List<B2ClientEntity> findByRealmId(String realmId){
		if(realmId==null) return null;
		NnV realmIdNV = NnV.inst(B2ClientEntity.getRealmIdField(), realmId);
		Long count = count(entTypeNV, realmIdNV);
		if(count<1) return Collections.emptyList();
		List<B2ClientEntity> clients = find(0, count.intValue(), entTypeNV, realmIdNV);
		if(clients.isEmpty()) return Collections.emptyList();
		return clients;
	}
	
	public B2ClientEntity findByRealmIdAndClientId(String realmId, String clientId){
		if(realmId==null || clientId==null) return null;
		NnV realmIdNV = NnV.inst(B2ClientEntity.getRealmIdField(), realmId);
		NnV clientIdNV = NnV.inst(B2ClientEntity.getClientIdField(), clientId);
		List<B2ClientEntity> clients = find(0, 1, entTypeNV, realmIdNV, clientIdNV);
		if(clients.isEmpty()) return null;
		return clients.iterator().next();
	}
	
	public List<B2ClientEntity> findByClientTemplate(String clientTemplateId){
		if(clientTemplateId==null) return null;
		NnV clientTemplateIdNV = NnV.inst(B2ClientEntity.getClientTemplateField(), clientTemplateId);
		Long count = count(entTypeNV, clientTemplateIdNV);
		if(count<1) return Collections.emptyList();
		List<B2ClientEntity> clients = find(0, count.intValue(), entTypeNV, clientTemplateIdNV);
		if(clients.isEmpty()) return Collections.emptyList();
		return clients;
	}
}
