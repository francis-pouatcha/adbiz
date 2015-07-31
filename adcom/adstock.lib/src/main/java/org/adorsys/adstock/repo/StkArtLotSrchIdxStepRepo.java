package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adstock.jpa.StkArtLotSrchIdxStep;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkArtLotSrchIdxStep.class)
public interface StkArtLotSrchIdxStepRepo extends CoreAbstEntityStepRepo<StkArtLotSrchIdxStep>{}
