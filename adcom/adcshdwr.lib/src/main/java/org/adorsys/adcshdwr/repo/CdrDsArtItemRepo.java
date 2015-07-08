package org.adorsys.adcshdwr.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcshdwr.jpa.CdrDsArtItem;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDsArtItem.class)
public interface CdrDsArtItemRepo extends CoreAbstBsnsItemRepo<CdrDsArtItem>{}
