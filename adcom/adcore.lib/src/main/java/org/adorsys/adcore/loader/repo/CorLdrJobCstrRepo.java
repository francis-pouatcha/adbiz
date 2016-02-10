package org.adorsys.adcore.loader.repo;

import org.adorsys.adcore.loader.jpa.CorLdrJobCstr;
import org.adorsys.adcore.repo.CoreAbstEntityCstrRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity=CorLdrJobCstr.class)
public interface CorLdrJobCstrRepo extends CoreAbstEntityCstrRepo<CorLdrJobCstr> {}
