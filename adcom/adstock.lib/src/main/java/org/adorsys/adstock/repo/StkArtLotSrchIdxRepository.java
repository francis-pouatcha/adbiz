package org.adorsys.adstock.repo;

import org.adorsys.adstock.jpa.StkArtLotSrchIdx;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkArtLotSrchIdx.class)
public interface StkArtLotSrchIdxRepository extends StkAbstArtLot2SctnRepository<StkArtLotSrchIdx>{

	public QueryResult<StkArtLotSrchIdx> findByArtLMIndex(String artLMIdx);
}
