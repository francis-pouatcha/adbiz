package org.adorsys.adstock.rest;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryEJB;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLotHstry;
import org.adorsys.adstock.jpa.StkJob;
import org.adorsys.adstock.jpa.StkStep;
import org.adorsys.adstock.repo.StkArticleLotHstryRepo;

@Stateless
public class StkArticleLotHstryEJB  extends CoreAbstBsnsObjectHstryEJB<CoreAbstBsnsObject, StkArticleLot, StkArticleLotHstry, 
	StkJob, StkStep, CoreAbstEntityCstr>{

	@Inject
	private StkArticleLotHstryRepo repo;

	@Resource
	private StkArticleLotInjector injector;
	
	@Override
	protected CoreAbstBsnsObjInjector<CoreAbstBsnsObject, StkArticleLot, 
		StkArticleLotHstry, StkJob, StkStep, CoreAbstEntityCstr> getInjector() {
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
}
