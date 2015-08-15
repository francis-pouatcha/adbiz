package org.adorsys.adcore.loader.repo;

import org.adorsys.adcore.loader.jpa.CorLdrPrcssngStep;
import org.adorsys.adcore.repo.CoreAbstPrcssngStepRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity=CorLdrPrcssngStep.class)
public interface CorLdrPrcssngStepRepo extends
		CoreAbstPrcssngStepRepo<CorLdrPrcssngStep> {
}
