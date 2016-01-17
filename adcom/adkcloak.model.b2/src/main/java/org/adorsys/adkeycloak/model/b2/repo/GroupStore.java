package org.adorsys.adkeycloak.model.b2.repo;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.adorsys.adcore.b2.jpa.NnV;
import org.adorsys.adkeycloak.model.b2.adapters.B2RealmProvider;
import org.adorsys.adkeycloak.model.b2.jpa.B2GroupEntity;

public class GroupStore extends B2AbstractRealmStore<B2GroupEntity> {

	public GroupStore(B2RealmProvider realmProvider, EntityManager em, int cacheSize) {
		super(realmProvider, em, cacheSize);
	}

	@Override
	protected Class<B2GroupEntity> getEntityClass() {
		return B2GroupEntity.class;
	}
	@Override
	protected String getEntityType() {
		return B2GroupEntity.ENT_TYPE;
	}
	
	public B2GroupEntity findByName(String name){
		if(name==null) return null;
		NnV nameNV = NnV.inst(B2GroupEntity.getNameField(), name);
		List<B2GroupEntity> entities = find(0,1,entTypeNV, nameNV);
		if(entities.isEmpty()) return null;
		return entities.iterator().next();
	}

	public Long countByRealmIdAndParentId(String realmId, String parentId) {
		if(realmId==null || parentId==null) return 0L;
		NnV realmIdNV = NnV.inst(B2GroupEntity.getRealmIdField(), realmId);
		NnV parentIdNV = NnV.inst(B2GroupEntity.getParentIdField(), parentId);
		return count(entTypeNV, realmIdNV, parentIdNV);
	}
	public List<B2GroupEntity> findByRealmIdAndParentId(String realmId, String parentId, int start, int max) {
		if(realmId==null || parentId==null) return Collections.emptyList();
		NnV realmIdNV = NnV.inst(B2GroupEntity.getRealmIdField(), realmId);
		NnV parentIdNV = NnV.inst(B2GroupEntity.getParentIdField(), parentId);
		return find(start, max, entTypeNV, realmIdNV, parentIdNV);
	}

	public Long countByRealmId(String realmId) {
		if(realmId==null) return 0L;
		NnV realmIdNV = NnV.inst(B2GroupEntity.getRealmIdField(), realmId);
		return count(entTypeNV, realmIdNV);
	}
	public List<B2GroupEntity> findByRealmId(String realmId, int start, int max) {
		if(realmId==null) return Collections.emptyList();
		NnV realmIdNV = NnV.inst(B2GroupEntity.getRealmIdField(), realmId);
		return find(start, max, entTypeNV, realmIdNV);
	}
}
