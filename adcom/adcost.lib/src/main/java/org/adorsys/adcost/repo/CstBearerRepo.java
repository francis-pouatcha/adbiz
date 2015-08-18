package org.adorsys.adcost.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcost.jpa.CstBearer;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CstBearer.class)
public interface CstBearerRepo extends CoreAbstIdentifRepo<CstBearer> {
}
