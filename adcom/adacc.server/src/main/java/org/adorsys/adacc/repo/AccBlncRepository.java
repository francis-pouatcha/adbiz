package org.adorsys.adacc.repo;

import org.adorsys.adacc.jpa.AccBlnc;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = AccBlnc.class)
public interface AccBlncRepository extends EntityRepository<AccBlnc, String>
{
}
