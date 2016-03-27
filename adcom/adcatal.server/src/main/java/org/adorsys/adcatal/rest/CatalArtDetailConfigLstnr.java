package org.adorsys.adcatal.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtDetailConfig;
import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcore.event.EntityCreatedEvent;
import org.adorsys.adcore.event.EntityDeletedEvent;

@Stateless
public class CatalArtDetailConfigLstnr {

	@Inject
	private CatalArticleEJB articleEJB;

	@Inject
	private CatalArticleLookup articleLookup;
	
	@Inject
	private CatalArtLangMappingLookup langMappingLookup;

	@Inject
	private CatalArtLangMappingEJB langMappingEJB;
	
	/**
	 * Creates an article upon creation of a detail config.
	 * 
	 * @param config
	 */
	public void handleCreatedEvent(@Observes @EntityCreatedEvent CatalArtDetailConfig config){
		
		// From the main article we can copy some usefull information into the derivative article.
		String cntnrIdentif = config.getCntnrIdentif();// Identif of the original.
		CatalArticle originalArticle = articleLookup.findByIdentif(cntnrIdentif);
		
		CatalArticle catalArticle = new CatalArticle();
		catalArticle.copyFrom(originalArticle);
		catalArticle.setSppu(config.getSppu());
		catalArticle.setIdentif(null);
		// This is the identifier of the original article.
		catalArticle.setCntnrIdentif(cntnrIdentif);
		catalArticle.setIdentif(config.getArtIdentif());
		catalArticle = articleEJB.create(catalArticle);
		Long count = langMappingLookup.countByCntnrIdentif(cntnrIdentif);
		List<CatalArtLangMapping> found = langMappingLookup.findByCntnrIdentif(cntnrIdentif, 0, count.intValue());
		for (CatalArtLangMapping origLangMapping : found) {
			CatalArtLangMapping langMapping = new CatalArtLangMapping();
			langMapping.copyFrom(origLangMapping);
			langMapping.setIdentif(null);
			langMapping.setArtName(origLangMapping.getArtName() + " - " + config.getQualifier());
			langMapping.setCntnrIdentif(catalArticle.getIdentif());
			langMappingEJB.create(langMapping);
		}
	}

	/**
	 * Creates an article upon creation of a detail config.
	 * 
	 * @param config
	 */
	public void handleDeletedEvent(@Observes @EntityDeletedEvent CatalArtDetailConfig config){
		articleEJB.deleteByIdentif(config.getArtIdentif());
	}
}
