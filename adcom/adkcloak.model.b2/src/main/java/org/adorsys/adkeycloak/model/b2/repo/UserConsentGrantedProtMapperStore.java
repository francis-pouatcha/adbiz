package org.adorsys.adkeycloak.model.b2.repo;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.adorsys.adcore.b2.jpa.NnV;
import org.adorsys.adkeycloak.model.b2.adapters.B2UserProvider;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserConsentGrantedProtMapper;

public class UserConsentGrantedProtMapperStore extends B2AbstractUserStore<B2UserConsentGrantedProtMapper> {

	public UserConsentGrantedProtMapperStore(B2UserProvider userProvider, EntityManager em, int cacheSize) {
		super(userProvider, em, cacheSize);
	}

	@Override
	protected Class<B2UserConsentGrantedProtMapper> getEntityClass() {
		return B2UserConsentGrantedProtMapper.class;
	}

	public Long countByProtocolMapperId(String protocolMapperId){
		if(protocolMapperId==null) return 0L;
		NnV protocolMapperIdNV = NnV.inst(B2UserConsentGrantedProtMapper.getProtocolMapperIdField(), protocolMapperId);
		return count(entTypeNV, protocolMapperIdNV);
	}
	public List<B2UserConsentGrantedProtMapper> findByByProtocolMapperId(String protocolMapperId, int start, int max){
		if(protocolMapperId==null) return Collections.emptyList();
		NnV protocolMapperIdNV = NnV.inst(B2UserConsentGrantedProtMapper.getProtocolMapperIdField(), protocolMapperId);
		return find(start, max, entTypeNV, protocolMapperIdNV);
	}

	@Override
	protected String getEntityType() {
		return B2UserConsentGrantedProtMapper.ENT_TYPE;
	}
}
