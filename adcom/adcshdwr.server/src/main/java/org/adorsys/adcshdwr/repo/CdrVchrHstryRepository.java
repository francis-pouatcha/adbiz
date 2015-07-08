package org.adorsys.adcshdwr.repo;

import org.adorsys.adcshdwr.jpa.CdrVchrHstry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrVchrHstry.class)
public interface CdrVchrHstryRepository extends EntityRepository<CdrVchrHstry, String>
{
}
