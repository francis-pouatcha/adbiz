package org.adorsys.adstock.repo;

import org.adorsys.adstock.jpa.StkArtLotMgmnt;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkArtLotMgmnt.class)
public interface StkArtLotMgmntRepo extends StkAbstArtLotMgmntRepo<StkArtLotMgmnt> {
}
