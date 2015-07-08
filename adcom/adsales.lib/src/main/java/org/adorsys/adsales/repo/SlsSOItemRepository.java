package org.adorsys.adsales.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adsales.jpa.SlsSOItem;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsSOItem.class)
public interface SlsSOItemRepository extends CoreAbstBsnsItemRepo<SlsSOItem>{}
