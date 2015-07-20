package org.adorsys.adcatal.rest;

import java.security.Principal;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.jpa.CatalArtLangMappingHstry;
import org.adorsys.adcatal.repo.CatalArtLangMappingHstryRepo;
import org.adorsys.adcore.auth.AdcomUser;
import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedHstryEJB;

@Stateless
public class CatalArtLangMappingHstryEJB  extends CoreAbstIdentifiedHstryEJB<CatalArtLangMappingHstry, CatalArtLangMapping>{

	@Inject
	private CatalArtLangMappingHstryRepo repo;
	@Resource
	private SessionContext sessionContext;

	@Override
	protected CoreAbstIdentifObjectHstryRepo<CatalArtLangMappingHstry> getRepo() {
		return repo;
	}

	@Override
	protected CatalArtLangMappingHstry newHstryObj() {
		return new CatalArtLangMappingHstry();
	}

	@Override
	protected AdcomUser getCallerPrincipal() {
		Principal callerPrincipal = sessionContext.getCallerPrincipal();
		if(callerPrincipal==null) return null;
		String name = callerPrincipal.getName();
		return new AdcomUser(name);
	}
}
