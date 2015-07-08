package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.SecTermCredtl;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SecTermCredtl.class)
public interface SecTermCredtlRepository extends EntityRepository<SecTermCredtl, String>
{
}
