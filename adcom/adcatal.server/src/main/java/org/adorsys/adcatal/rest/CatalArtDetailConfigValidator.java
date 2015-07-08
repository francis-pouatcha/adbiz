package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtDetailConfig;
import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcore.event.EntityCreatedEvent;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CatalArtDetailConfigValidator {
	@Inject
	private CatalArticleLookup catalArticleLookup;
	
	protected void handleEntityCreatedEvent(@Observes @EntityCreatedEvent CatalArtDetailConfig detailConfig){
		String artPic = detailConfig.getPic();
		if (StringUtils.isBlank(artPic))
			throw new IllegalArgumentException(
					"The article pic should not be null here.");
		CatalArticle catalArticle = catalArticleLookup.findByIdentif(artPic);
		if (catalArticle == null)
			throw new IllegalStateException("Not article with pic : " + artPic);
		detailConfig.setPic(artPic);
		detailConfig.setVatRate(catalArticle.getVatRate());
	}
}
