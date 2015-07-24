package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalProdFmly;
import org.adorsys.adcatal.jpa.CatalProdFmlyLangMap;
import org.adorsys.adcore.rest.CoreAbstContainerDeletedLstnr;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class CatalProdFmlyLangMapDlteLstnr  extends CoreAbstContainerDeletedLstnr<CatalProdFmly, CatalProdFmlyLangMap>{

	@Inject
	private CatalProdFmlyLangMapEJB cntnrEJB;
	
	@Inject
	private CatalProdFmlyLangMapLookup cntnrLookup;

	@Override
	protected CoreAbstIdentifiedEJB<CatalProdFmlyLangMap> getEltEjb() {
		return cntnrEJB;
	}

	@Override
	protected CoreAbstIdentifLookup<CatalProdFmlyLangMap> getEltLookup() {
		return cntnrLookup;
	}
}
