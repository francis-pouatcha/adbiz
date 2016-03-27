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
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

public abstract class CoreAbstBsnsItemArchiveExecTask<E extends CoreAbstBsnsObject, EA extends CoreAbstBsnsObject, I extends CoreAbstBsnsItem, IA extends CoreAbstBsnsItem,
H extends CoreAbstBsnsObjectHstry, HA extends CoreAbstBsnsObjectHstry, J extends CoreAbstEntityJob,
S extends CoreAbstEntityStep, C extends CoreAbstEntityCstr, PS extends CoreAbstPrcssngStep> extends CoreAbstEntityJobExecutor<J, S, PS>{
	
	protected abstract CoreAbstBsnsObjInjector<E, I, H, J, S, C> getInjector();
	protected abstract IA newArchiveEntity();
	protected abstract CoreAbstIdentifiedEJB<IA> getItemArchiveEjb();
	
	@Override
	public void execute(String stepIdentif) {
		S step = getInjector().getStepLookup().findByIdentif(stepIdentif);// Refresh the step object
		archiveItems(step.getCntnrIdentif(), step.getIdentif());
	}

	private void archiveItems(String jobIdentif, String stepIdentif) {
		S step = getInjector().getStepLookup().findByIdentif(stepIdentif);// Refresh the step object
		Long count = getInjector().getItemLookup().countByCntnrIdentifAndIdentifBetween(step.getEntIdentif(), step.getStepStartId(), step.getStepEndId());
		if(count>0){
			List<I> list = getInjector().getItemLookup().findByCntnrIdentifAndIdentifBetweenOrderByIdentifAsc(step.getEntIdentif(), step.getStepStartId(), step.getStepEndId(), 0, count.intValue());
			for (I e : list) {
				IA archiveEntity = newArchiveEntity();
				archiveEntity.copyFrom(e);
				archiveEntity.setId(e.getId());
				getItemArchiveEjb().create(archiveEntity);
				getInjector().getItemEjb().archiveById(e.getId());
			}
		}
		step.setEnded(new Date());
		getInjector().getStepEjb().update(step);
	}
}

