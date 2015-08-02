package org.adorsys.adstock.rest;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryEJB;
import org.adorsys.adstock.jpa.StkJob;
import org.adorsys.adstock.jpa.StkMvnt;
import org.adorsys.adstock.jpa.StkMvntHstry;
import org.adorsys.adstock.jpa.StkStep;
import org.adorsys.adstock.repo.StkMvntHstryRepo;

@Stateless
public class StkMvntHstryEJB  extends CoreAbstBsnsObjectHstryEJB<CoreAbstBsnsObject, StkMvnt, StkMvntHstry, 
	StkJob, StkStep, CoreAbstEntityCstr>{

	@Inject
	private StkMvntHstryRepo repo;
	@Resource
	private StkMvntInjector injector;
	
	@Override
	protected CoreAbstBsnsObjInjector<CoreAbstBsnsObject, StkMvnt, 
		StkMvntHstry, StkJob, StkStep, CoreAbstEntityCstr> getInjector() {
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
}
