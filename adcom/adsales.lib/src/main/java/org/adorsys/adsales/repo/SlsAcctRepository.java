package org.adorsys.adsales.repo;

import org.adorsys.adsales.jpa.SlsAcct;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsAcct.class)
public interface SlsAcctRepository extends EntityRepository<SlsAcct, String>
{
}
