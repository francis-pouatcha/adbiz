package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcore.rest.CoreAbstContainerDeletedLstnr;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class CatalArtLangMappingDlteLstnr  extends CoreAbstContainerDeletedLstnr<CatalArticle, CatalArtLangMapping>{

	@Inject
	private CatalArtLangMappingEJB cntnrEJB;
	
	@Inject
	private CatalArtLangMappingLookup cntnrLookup;

	@Override
	protected CoreAbstIdentifiedEJB<CatalArtLangMapping> getEltEjb() {
		return cntnrEJB;
	}

	@Override
	protected CoreAbstIdentifLookup<CatalArtLangMapping> getEltLookup() {
		return cntnrLookup;
	}
}
