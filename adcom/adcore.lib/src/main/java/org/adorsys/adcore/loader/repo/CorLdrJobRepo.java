package org.adorsys.adcore.loader.repo;

import org.adorsys.adcore.loader.jpa.CorLdrJob;
import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity=CorLdrJob.class)
public interface CorLdrJobRepo extends
		CoreAbstEntityJobRepo<CorLdrJob> {
}
