package org.adorsys.adstock.rest;

import java.security.Principal;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.auth.AdcomUser;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryEJB;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLotHstry;
import org.adorsys.adstock.jpa.StkArticleLotJob;
import org.adorsys.adstock.jpa.StkArticleLotStep;
import org.adorsys.adstock.repo.StkArticleLotHstryRepo;

@Stateless
public class StkArticleLotHstryEJB  extends CoreAbstBsnsObjectHstryEJB<CoreAbstBsnsObject, StkArticleLot, StkArticleLotHstry, 
	StkArticleLotJob, StkArticleLotStep, CoreAbstEntityCstr>{

	@Inject
	private StkArticleLotHstryRepo repo;
	@Resource
	private SessionContext sessionContext;
	@Resource
	private StkArticleLotInjector injector;
	
	@Override
	protected CoreAbstBsnsObjInjector<CoreAbstBsnsObject, StkArticleLot, 
		StkArticleLotHstry, StkArticleLotJob, StkArticleLotStep, CoreAbstEntityCstr> getInjector() {
		return injector;
	}

	@Override
	protected CoreAbstIdentifObjectHstryRepo<StkArticleLotHstry> getRepo() {
		return repo;
	}

	@Override
	protected StkArticleLotHstry newHstryObj() {
		return new StkArticleLotHstry();
	}

	@Override
	protected AdcomUser getCallerPrincipal() {
		Principal callerPrincipal = sessionContext.getCallerPrincipal();
		if(callerPrincipal==null) return null;
		String name = callerPrincipal.getName();
		return new AdcomUser(name);
	}
}
