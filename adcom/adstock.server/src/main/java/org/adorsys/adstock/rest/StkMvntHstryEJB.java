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
import org.adorsys.adstock.jpa.StkMvnt;
import org.adorsys.adstock.jpa.StkMvntHstry;
import org.adorsys.adstock.jpa.StkMvntJob;
import org.adorsys.adstock.jpa.StkMvntStep;
import org.adorsys.adstock.repo.StkMvntHstryRepo;

@Stateless
public class StkMvntHstryEJB  extends CoreAbstBsnsObjectHstryEJB<CoreAbstBsnsObject, StkMvnt, StkMvntHstry, 
	StkMvntJob, StkMvntStep, CoreAbstEntityCstr>{

	@Inject
	private StkMvntHstryRepo repo;
	@Resource
	private SessionContext sessionContext;
	@Resource
	private StkMvntInjector injector;
	
	@Override
	protected CoreAbstBsnsObjInjector<CoreAbstBsnsObject, StkMvnt, 
		StkMvntHstry, StkMvntJob, StkMvntStep, CoreAbstEntityCstr> getInjector() {
		return injector;
	}

	@Override
	protected CoreAbstIdentifObjectHstryRepo<StkMvntHstry> getRepo() {
		return repo;
	}

	@Override
	protected StkMvntHstry newHstryObj() {
		return new StkMvntHstry();
	}

	@Override
	protected AdcomUser getCallerPrincipal() {
		Principal callerPrincipal = sessionContext.getCallerPrincipal();
		if(callerPrincipal==null) return null;
		String name = callerPrincipal.getName();
		return new AdcomUser(name);
	}
}
