package org.adorsys.adcore.task;

import java.util.List;

import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.adorsys.adcore.jpa.CoreAbstPrcssngStep;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryLookup;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedHstryEJB;

public abstract class CoreAbstHstryDeleteExecTask<E extends CoreAbstBsnsObject, 
	H extends CoreAbstBsnsObjectHstry, J extends CoreAbstEntityJob, S extends CoreAbstEntityStep, PS extends CoreAbstPrcssngStep>
		extends CoreAbstEntityJobExecutor<J, S, PS> {

	public abstract CoreAbstEntityStepLookup<S> getStepLookup();
	public abstract CoreAbstBsnsObjectHstryLookup<H> getHstrLookup();
	public abstract CoreAbstIdentifiedHstryEJB<H,E> getHstrEjb();

	@Override
	public void execute(String stepIdentif) {
		S step = getStepLookup().findByIdentif(stepIdentif);
		deleteHstries(step.getCntnrIdentif(), step.getIdentif());
		getBatch().terminateStep(stepIdentif);
	}

	private void deleteHstries(String jobIdentif, String stepIdentif) {
		S step = getStepLookup().findByIdentif(stepIdentif);
		// object
		String entIdentif = step.getEntIdentif();
		Long count = getHstrLookup()
				.countByEntIdentifAndIdBetween(entIdentif,
						step.getStepStartId(), step.getStepEndId());
		if (count > 0) {
			List<H> list = getHstrLookup()
					.findByEntIdentifAndIdBetweenOrderByIdAsc(entIdentif,
							step.getStepStartId(), step.getStepEndId(), 0,
							count.intValue());
			for (H h : list) {
				getHstrEjb().deleteById(h.getId());
			}
		}


	}
}
