package org.adorsys.adkeycloak.model.b2.repo;

import java.util.List;

import javax.persistence.EntityManager;

import org.adorsys.adcore.b2.jpa.NnV;
import org.adorsys.adkeycloak.model.b2.adapters.B2RealmProvider;
import org.adorsys.adkeycloak.model.b2.jpa.B2RealmEntity;

public class RealmStore extends B2AbstractRealmStore<B2RealmEntity> {

	public RealmStore(B2RealmProvider realmProvider, EntityManager em, int cacheSize) {
		super(realmProvider, em, cacheSize);
	}

	@Override
	protected Class<B2RealmEntity> getEntityClass() {
		return B2RealmEntity.class;
	}
	
	@Override
	protected String getEntityType() {
		return B2RealmEntity.ENT_TYPE;
	}

	public B2RealmEntity findByName(String name){
		if(name==null) return null;
		NnV nameNV = NnV.inst(B2RealmEntity.getNameField(), name);
		List<B2RealmEntity> entities = find(0,1,entTypeNV, nameNV);
		if(entities.isEmpty()) return null;
		return entities.iterator().next();
	}

}
