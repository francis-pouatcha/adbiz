package org.adorsys.adcore.loader.repo;

import org.adorsys.adcore.loader.jpa.CorLdrStep;
import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity=CorLdrStep.class)
public interface CorLdrStepRepo extends
		CoreAbstEntityStepRepo<CorLdrStep> {
}
