package org.adorsys.adacc.repo;

import org.adorsys.adacc.jpa.AccCoA;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = AccCoA.class)
public interface AccCoARepository extends EntityRepository<AccCoA, String>
{
}
