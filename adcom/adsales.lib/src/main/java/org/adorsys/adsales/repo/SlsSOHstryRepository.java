package org.adorsys.adsales.repo;

import org.adorsys.adsales.jpa.SlsSOHstry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsSOHstry.class)
public interface SlsSOHstryRepository extends EntityRepository<SlsSOHstry, String>
{
}
