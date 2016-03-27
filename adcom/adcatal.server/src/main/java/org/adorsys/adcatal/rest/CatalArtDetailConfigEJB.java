package org.adorsys.adcatal.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtDetailConfig;
import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcatal.repo.CatalArtDetailConfigRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.apache.commons.lang3.RandomStringUtils;

@Stateless
public class CatalArtDetailConfigEJB extends
		CoreAbstIdentifiedEJB<CatalArtDetailConfig> {

	@Inject
	private CatalArtDetailConfigRepository repository;

	@Inject
	private CatalArticleLookup articleLookup;
	@Override
	protected CoreAbstIdentifRepo<CatalArtDetailConfig> getRepo() {
		return repository;
	}

	@Override
	public CatalArtDetailConfig create(CatalArtDetailConfig entity) {
		String cntnrIdentif = entity.getCntnrIdentif();
		String artIdentif = null;
		Long compositeCount = articleLookup.countByCntnrIdentif(cntnrIdentif);
		if(compositeCount>0){
			List<CatalArticle> composites = articleLookup.findByCntnrIdentif(cntnrIdentif, 0, compositeCount.intValue());
			Set<String> identifSet = new HashSet<>();
			for (CatalArticle catalArticle : composites) {
				identifSet.add(catalArticle.getIdentif());
			}
			int count = 1000;// Number of iterations to find a unique identifier.
			// The container identifier of a derived article is the identifier of the original article
			while (count>0){
				count=count-1;
				artIdentif = cntnrIdentif + "x" + RandomStringUtils.randomNumeric(3);
				if(!identifSet.contains(artIdentif)) break;
			}
		}
		if(artIdentif==null)
			artIdentif = cntnrIdentif + "x" + RandomStringUtils.randomNumeric(3);
		
		entity.setArtIdentif(artIdentif);
		return super.create(entity);
	}

}
