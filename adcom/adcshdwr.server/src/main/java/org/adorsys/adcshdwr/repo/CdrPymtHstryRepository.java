package org.adorsys.adcshdwr.repo;

import org.adorsys.adcshdwr.jpa.CdrPymtHstry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrPymtHstry.class)
public interface CdrPymtHstryRepository extends EntityRepository<CdrPymtHstry, String>
{
}
