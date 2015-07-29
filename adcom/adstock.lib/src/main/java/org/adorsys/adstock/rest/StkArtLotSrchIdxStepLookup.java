package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adstock.jpa.StkArtLotSrchIdxStep;
import org.adorsys.adstock.repo.StkArtLotSrchIdxStepRepo;

@Stateless
public class StkArtLotSrchIdxStepLookup extends CoreAbstEntityStepLookup<StkArtLotSrchIdxStep> {

	@Inject
	private StkArtLotSrchIdxStepRepo repository;

	@Override
	protected CoreAbstEntityStepRepo<StkArtLotSrchIdxStep> getStepRepo() {
		return repository;
	}

	@Override
	protected Class<StkArtLotSrchIdxStep> getEntityClass() {
		return StkArtLotSrchIdxStep.class;
	}

}
