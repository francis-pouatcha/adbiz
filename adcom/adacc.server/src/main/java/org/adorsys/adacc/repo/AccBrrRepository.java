package org.adorsys.adacc.repo;

import org.adorsys.adacc.jpa.AccBrr;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = AccBrr.class)
public interface AccBrrRepository extends EntityRepository<AccBrr, String>
{
}
