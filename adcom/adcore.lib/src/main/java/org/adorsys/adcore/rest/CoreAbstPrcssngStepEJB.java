package org.adorsys.adcore.rest;

import javax.persistence.LockModeType;

import org.adorsys.adcore.jpa.CoreAbstPrcssngStep;

public abstract class CoreAbstPrcssngStepEJB<PS extends CoreAbstPrcssngStep>
		extends CoreAbstIdentifiedEJB<PS> {

	public abstract PS newInstance();
	
	public PS updateFlushAndRefresh(PS entity) {
		return getRepo().saveAndFlushAndRefresh(attach(entity));
	}
	
	public void lock(PS ps, LockModeType lockMode){
		getRepo().lock(ps, lockMode);
	}

}
