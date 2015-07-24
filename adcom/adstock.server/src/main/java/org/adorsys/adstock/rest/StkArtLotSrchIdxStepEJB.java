package org.adorsys.adstock.rest;

import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adstock.jpa.StkArtLotSrchIdxStep;
import org.adorsys.adstock.repo.StkArtLotSrchIdxStepRepo;

public class StkArtLotSrchIdxStepEJB extends CoreAbstEntityStepEJB<StkArtLotSrchIdxStep>{
	@Inject
	private StkArtLotSrchIdxStepRepo repository;

	@Override
	public StkArtLotSrchIdxStep newStepInstance() {
		return new StkArtLotSrchIdxStep();
	}

	@Override
	protected CoreAbstIdentifRepo<StkArtLotSrchIdxStep> getRepo() {
		return repository;
	}

}
