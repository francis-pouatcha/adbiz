package org.adorsys.adkeycloak.model.b2.repo;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.adorsys.adcore.b2.jpa.NnV;
import org.adorsys.adkeycloak.model.b2.adapters.B2RealmProvider;
import org.adorsys.adkeycloak.model.b2.jpa.B2RoleEntity;

public class RoleStore extends B2AbstractRealmStore<B2RoleEntity> {

	public RoleStore(B2RealmProvider realmProvider, EntityManager em, int cacheSize) {
		super(realmProvider, em, cacheSize);
	}

	@Override
	protected Class<B2RoleEntity> getEntityClass() {
		return B2RoleEntity.class;
	}

	@Override
	protected String getEntityType() {
		return B2RoleEntity.ENT_TYPE;
	}

	public B2RoleEntity findByNameAndRootId(String name, String realmId){
		if(name==null || realmId==null) return null;
		NnV nameNV = NnV.inst(B2RoleEntity.getNameField(), name);
		NnV rootIdNV = NnV.inst(B2RoleEntity.getRootIdField(), realmId);
		List<B2RoleEntity> entities = find(0,1,entTypeNV, nameNV, rootIdNV);
		if(entities.isEmpty()) return null;
		return entities.iterator().next();
	}
	
	public B2RoleEntity findByNameAndRealmId(String name, String realmId){
		if(name==null || realmId==null) return null;
		NnV nameNV = NnV.inst(B2RoleEntity.getNameField(), name);
		NnV realmIdNV = NnV.inst(B2RoleEntity.getRealmIdField(), realmId);
		List<B2RoleEntity> entities = find(0,1,entTypeNV, nameNV, realmIdNV);
		if(entities.isEmpty()) return null;
		return entities.iterator().next();
	}

	public B2RoleEntity findByNameAndClientId(String name, String clientId){
		if(name==null || clientId==null) return null;
		NnV nameNV = NnV.inst(B2RoleEntity.getNameField(), name);
		NnV clientIdNV = NnV.inst(B2RoleEntity.getClientIdField(), clientId);
		List<B2RoleEntity> entities = find(0,1,entTypeNV, nameNV, clientIdNV);
		if(entities.isEmpty()) return null;
		return entities.iterator().next();
	}

	public Long countByClientId(String clientId){
		if(clientId==null) return 0L;
		NnV clientIdNV = NnV.inst(B2RoleEntity.getClientIdField(), clientId);
		return count(entTypeNV, clientIdNV);
	}
	public List<B2RoleEntity> findByClientId(String clientId, int start, int max){
		if(clientId==null) return Collections.emptyList();
		NnV clientIdNV = NnV.inst(B2RoleEntity.getClientIdField(), clientId);
		return find(start,max,entTypeNV, clientIdNV);
	}

	public Long countByRealmId(String realmId){
		if(realmId==null) return 0L;
		NnV realmIdNV = NnV.inst(B2RoleEntity.getRealmIdField(), realmId);
		return count(entTypeNV, realmIdNV);
	}
	public List<B2RoleEntity> findByRealmId(String realmId, int start, int max){
		if(realmId==null) return Collections.emptyList();
		NnV realmIdNV = NnV.inst(B2RoleEntity.getRealmIdField(), realmId);
		return find(0,1,entTypeNV, realmIdNV);
	}
}
