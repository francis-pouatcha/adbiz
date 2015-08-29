package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.SecTermCredtl;
import org.adorsys.adcore.repo.CoreAbstEntityRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SecTermCredtl.class)
public interface SecTermCredtlRepository extends CoreAbstEntityRepo<SecTermCredtl>{}
