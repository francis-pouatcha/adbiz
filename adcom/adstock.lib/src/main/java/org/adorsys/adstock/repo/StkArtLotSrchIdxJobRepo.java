package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adstock.jpa.StkArtLotSrchIdxJob;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkArtLotSrchIdxJob.class)
public interface StkArtLotSrchIdxJobRepo extends CoreAbstEntityJobRepo<StkArtLotSrchIdxJob>{}
