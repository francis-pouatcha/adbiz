package org.adorsys.adcore.task;

import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adcore.xls.StepCallback;

public abstract class CoreAbstStepCallback<J extends CoreAbstEntityJob, S extends CoreAbstEntityStep> implements StepCallback {
	protected abstract CoreAbstEntityStepLookup<S> getStepLookup();
	protected abstract CoreAbstEntityStepEJB<S> getStepEJB();
	@Override
	public int readSyncPoint(String stepIdentif) {
		S s = getStepLookup().findByIdentif(stepIdentif);
		if(s!=null) return s.getSynchPointStart()==null?0:new Integer(s.getSynchPointStart());
		return 0;
	}
	
	@Override
	public void markSynchPoint(String stepIdentif, int rowNum) {
		S s = getStepLookup().findByIdentif(stepIdentif);
		if(s!=null){
			s.setSynchPointStart(""+rowNum);
			s.setSynchPointEnd(""+rowNum);
			getStepEJB().update(s);
		}
	}
}
