package org.adorsys.adsales.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adsales.jpa.SlsDlvryItem;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsDlvryItem.class)
public interface SlsDlvryItemRepository extends CoreAbstBsnsItemRepo<SlsDlvryItem>{}
