package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adstock.jpa.StkArtLotSrchIdx;
import org.adorsys.adstock.repo.StkArtLotSrchIdxRepository;

/**
 * 
 * @author francis
 *
 */
@Stateless
public class StkArtLotSrchIdxEJB extends
	CoreAbstIdentifiedEJB<StkArtLotSrchIdx> {
	@Inject
	private StkArtLotSrchIdxRepository repository;
	@Override
	protected CoreAbstIdentifRepo<StkArtLotSrchIdx> getRepo() {
		return repository;
	}
	
}
