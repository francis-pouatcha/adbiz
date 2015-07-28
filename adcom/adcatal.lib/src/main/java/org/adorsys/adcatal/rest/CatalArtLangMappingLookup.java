package org.adorsys.adcatal.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.repo.CatalArtLangMappingRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;

@Stateless
public class CatalArtLangMappingLookup  extends CatalAbstArtLangMapLookup<CatalArtLangMapping>{

	@Inject
	private CatalArtLangMappingRepository repository;

	@Override
	protected CoreAbstIdentifRepo<CatalArtLangMapping> getRepo() {
		return repository;
	}

	@Override
	protected Class<CatalArtLangMapping> getEntityClass() {
		return CatalArtLangMapping.class;
	}
	
	public Long countByArtPic(String artPic){
		return countByCntnrIdentif(artPic);
	}
	public List<CatalArtLangMapping> findByArtPic(String artPic, int start, int max){
		return findByCntnrIdentif(artPic, start, max);
	}
}
