package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryLookup;
import org.adorsys.adstock.jpa.StkMvntHstryGtwy;
import org.adorsys.adstock.repo.StkMvntHstryGtwyRepo;

@Stateless
public class StkMvntHstryGtwyLookup  extends CoreAbstBsnsObjectHstryLookup<StkMvntHstryGtwy>{

	@Inject
	private StkMvntHstryGtwyRepo repo;

	@Override
	protected CoreAbstBsnsObjHstryRepo<StkMvntHstryGtwy> getRepo() {
		return repo;
	}

}
