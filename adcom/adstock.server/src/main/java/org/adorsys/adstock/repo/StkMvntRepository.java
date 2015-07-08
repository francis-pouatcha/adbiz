package org.adorsys.adstock.repo;

import org.adorsys.adstock.jpa.StkMvnt;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkMvnt.class)
public interface StkMvntRepository extends EntityRepository<StkMvnt, String>
{
}
