package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adstock.jpa.StkArtLotSrchIdxJob;
import org.adorsys.adstock.repo.StkArtLotSrchIdxJobRepo;

@Stateless
public class StkArtLotSrchIdxJobLookup extends CoreAbstEntityJobLookup<StkArtLotSrchIdxJob>{

	@Inject
	private StkArtLotSrchIdxJobRepo repository;

	@Override
	protected CoreAbstEntityJobRepo<StkArtLotSrchIdxJob> getJobRepo() {
		return repository;
	}

	@Override
	protected Class<StkArtLotSrchIdxJob> getEntityClass() {
		return StkArtLotSrchIdxJob.class;
	}

}
