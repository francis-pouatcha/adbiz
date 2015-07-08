package org.adorsys.adsales.repo;

import org.adorsys.adsales.jpa.SlsInvceHistory;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsInvceHistory.class)
public interface SlsInvceHistoryRepository extends EntityRepository<SlsInvceHistory, String>
{
}
