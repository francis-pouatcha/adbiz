package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalProdFmlyLangMap;
import org.adorsys.adcatal.repo.CatalProdFmlyLangMapRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class CatalProdFmlyLangMapEJB  extends CoreAbstIdentifiedEJB<CatalProdFmlyLangMap>{

	@Inject
	private CatalProdFmlyLangMapRepo repository;

	@Override
	protected CoreAbstIdentifDataRepo<CatalProdFmlyLangMap> getRepo() {
		return repository;
	}
}
