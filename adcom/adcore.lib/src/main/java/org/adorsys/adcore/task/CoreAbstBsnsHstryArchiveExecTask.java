package org.adorsys.adcore.task;

import java.util.Date;
import java.util.List;

import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.adorsys.adcore.jpa.CoreAbstPrcssngStep;
import org.adorsys.adcore.rest.CoreAbstArchiveHstryEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;

public abstract class CoreAbstBsnsHstryArchiveExecTask<E extends CoreAbstBsnsObject, EA extends CoreAbstBsnsObject, I extends CoreAbstBsnsItem, IA extends CoreAbstBsnsItem,
H extends CoreAbstBsnsObjectHstry, HA extends CoreAbstBsnsObjectHstry, J extends CoreAbstEntityJob,
S extends CoreAbstEntityStep, C extends CoreAbstEntityCstr, PS extends CoreAbstPrcssngStep> extends CoreAbstEntityJobExecutor<J, S, PS>{
	
	protected abstract CoreAbstBsnsObjInjector<E, I, H, J, S, C> getInjector();
	protected abstract HA newHstryEntity();
	protected abstract CoreAbstArchiveHstryEJB<HA> getHstryArchiveEjb();

	@Override
	public void execute(String stepIdentif) {
		S step = getInjector().getStepLookup().findByIdentif(stepIdentif);// Refresh the step object
		archiveHstry(step.getCntnrIdentif(), step.getIdentif());
	}

	private void archiveHstry(String jobIdentif, String stepIdentif) {
		S step = getInjector().getStepLookup().findByIdentif(stepIdentif);// Refresh the step object
		Long count = getInjector().getHstrLookup().countByEntIdentifAndIdBetween(step.getEntIdentif(), step.getStepStartId(), step.getStepEndId());
		if(count>0){
			List<H> list = getInjector().getHstrLookup().findByEntIdentifAndIdBetweenOrderByIdAsc(step.getEntIdentif(), step.getStepStartId(), step.getStepEndId(), 0, count.intValue());
			for (H h : list) {
				HA ha = newHstryEntity();
				ha.copyFrom(h);
				ha.setId(h.getId());
				getHstryArchiveEjb().create(ha);
				getInjector().getHstrEjb().archiveById(h.getId());
			}
		}
		step.setEnded(new Date());
		getInjector().getStepEjb().update(step);
	}
}

