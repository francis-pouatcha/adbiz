package org.adorsys.adbnsptnr.repo;

import org.adorsys.adbnsptnr.jpa.BpPtnrAccntBlnce;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BpPtnrAccntBlnce.class)
public interface BpPtnrAccntBlnceRepository extends EntityRepository<BpPtnrAccntBlnce, String>
{
}
