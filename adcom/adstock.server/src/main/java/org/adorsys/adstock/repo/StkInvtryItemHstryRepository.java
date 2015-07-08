package org.adorsys.adstock.repo;

import org.adorsys.adstock.jpa.StkInvtryItemHstry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkInvtryItemHstry.class)
public interface StkInvtryItemHstryRepository extends EntityRepository<StkInvtryItemHstry, String>
{
}
