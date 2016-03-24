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
	
//	public Long countByArtNameLike(String artName, int start, int max){
//
//		CriteriaBuilder cb = repository.getCriteriaBuilder();
//		CriteriaQuery<Long> c = cb.createQuery(Long.class);//the query returns a long, and not Orders
//		Root<CatalArtLangMapping> root = c.from( CatalArtLangMapping.class );
//		//c.select(cb.countDistinct(order));//and this is the code you were looking for
//		c.select(cb.countDistinct(root.get("cntnrIdentif")));//for counting distinct fields other than the primary key
//		Path<String> artNamePath = root.get( "artName" );
//		CriteriaQuery<Long> where = c.where( cb.like(artNamePath, artName ) ).;
////		;
//		repository.criteria().likeIgnoreCase(CatalArtLangMapping_.artName, artName ).distinct().getSingleResult();
//
//	}
	
}
