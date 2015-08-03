package org.adorsys.adstock.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adstock.jpa.StkJob;
import org.adorsys.adstock.jpa.StkMvnt;
import org.adorsys.adstock.jpa.StkMvntHstry;
import org.adorsys.adstock.jpa.StkStep;
import org.adorsys.adstock.repo.StkMvntRepository;

@Stateless
public class StkMvntEJB extends CoreAbstBsnsItemEJB<CoreAbstBsnsObject, StkMvnt, StkMvntHstry, StkJob, StkStep, CoreAbstEntityCstr> {

	@Inject
	private StkMvntRepository repository;
	@EJB
	private StkMvntInjector injector;

	@Override
	protected CoreAbstBsnsItemRepo<StkMvnt> getBsnsRepo() {
		return repository;
	}

	@Override
	protected CoreAbstBsnsObjInjector<CoreAbstBsnsObject, StkMvnt, StkMvntHstry, StkJob, StkStep, CoreAbstEntityCstr> getInjector() {
		return injector;
	}
}
