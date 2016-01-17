package org.adorsys.adkeycloak.model.b2.repo;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.adorsys.adcore.b2.jpa.NnV;
import org.adorsys.adkeycloak.model.b2.adapters.B2RealmProvider;
import org.adorsys.adkeycloak.model.b2.jpa.B2ClientTemplateEntity;

public class ClientTemplateStore extends B2AbstractRealmStore<B2ClientTemplateEntity> {

	public ClientTemplateStore(B2RealmProvider realmProvider, EntityManager em, int cacheSize) {
		super(realmProvider, em, cacheSize);
	}

	@Override
	protected Class<B2ClientTemplateEntity> getEntityClass() {
		return B2ClientTemplateEntity.class;
	}
	@Override
	protected String getEntityType() {
		return B2ClientTemplateEntity.ENT_TYPE;
	}

	
	public B2ClientTemplateEntity findByRealmIdAndName(String realmId, String name){
		if(realmId==null || name==null) return null;
		NnV realmIdNV = NnV.inst(B2ClientTemplateEntity.getRealmIdField(), realmId);
		NnV nameNV = NnV.inst(B2ClientTemplateEntity.getNameField(), name);
		List<B2ClientTemplateEntity> found = find(0, 1, entTypeNV, realmIdNV, nameNV);
		if(found.isEmpty()) return null;
		return found.iterator().next();
	}

	public List<B2ClientTemplateEntity> findByRealmId(String realmId){
		if(realmId==null) return null;
		NnV realmIdNV = NnV.inst(B2ClientTemplateEntity.getRealmIdField(), realmId);
		Long count = count(entTypeNV, realmIdNV);
		if(count<1) return Collections.emptyList();
		List<B2ClientTemplateEntity> clients = find(0, count.intValue(), entTypeNV, realmIdNV);
		if(clients.isEmpty()) return Collections.emptyList();
		return clients;
	}
}
