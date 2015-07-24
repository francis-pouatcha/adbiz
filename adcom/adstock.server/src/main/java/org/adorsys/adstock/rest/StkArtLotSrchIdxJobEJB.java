package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adstock.jpa.StkArtLotSrchIdxJob;
import org.adorsys.adstock.repo.StkArtLotSrchIdxJobRepo;

@Stateless
public class StkArtLotSrchIdxJobEJB extends CoreAbstEntityJobEJB<StkArtLotSrchIdxJob> {

	@Inject
	private StkArtLotSrchIdxJobRepo jobRepo;
	
	@Override
	public StkArtLotSrchIdxJob newJobInstance() {
		return new StkArtLotSrchIdxJob();
	}

	@Override
	protected CoreAbstIdentifRepo<StkArtLotSrchIdxJob> getRepo() {
		return jobRepo;
	}
}
