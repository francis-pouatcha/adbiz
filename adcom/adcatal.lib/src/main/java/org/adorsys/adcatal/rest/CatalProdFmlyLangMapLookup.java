package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalProdFmlyLangMap;
import org.adorsys.adcatal.repo.CatalProdFmlyLangMapRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;

@Stateless
public class CatalProdFmlyLangMapLookup  extends CatalAbstArtLangMapLookup<CatalProdFmlyLangMap>{

	@Inject
	private CatalProdFmlyLangMapRepo repository;

	@Override
	protected CoreAbstIdentifDataRepo<CatalProdFmlyLangMap> getRepo() {
		return repository;
	}

	@Override
	protected Class<CatalProdFmlyLangMap> getEntityClass() {
		return CatalProdFmlyLangMap.class;
	}
}
