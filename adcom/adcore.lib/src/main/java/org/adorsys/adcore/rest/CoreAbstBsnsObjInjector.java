package org.adorsys.adcore.rest;

import org.adorsys.adcore.auth.AdcomUser;
import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;

public abstract class CoreAbstBsnsObjInjector<
	E extends CoreAbstBsnsObject, I extends CoreAbstBsnsItem, 
	H extends CoreAbstBsnsObjectHstry, J extends CoreAbstEntityJob,
	S extends CoreAbstEntityStep, C extends CoreAbstEntityCstr> {

	protected abstract CoreAbstBsnsObjectLookup<E> getBsnsObjLookup();
	protected abstract CoreAbstBsnsObjectEJB<E, I, H, J, S, C> getBsnsObjEjb();
	protected abstract CoreAbstBsnsItemLookup<I> getItemLookup();
	protected abstract CoreAbstBsnsItemEJB<E, I, H, J, S, C> getItemEjb();
	protected abstract CoreAbstBsnsObjectHstryLookup<H> getHstrLookup();
	protected abstract CoreAbstBsnsObjectHstryEJB<E, I, H, J, S, C> getHstrEjb();
	protected abstract String getSequenceGeneratorPrefix();
	protected abstract AdcomUser getCallerPrincipal();
	protected abstract CoreAbstEntityJobEJB<J> getJobEjb();
	protected abstract CoreAbstEntityJobLookup<J> getJobLookup();
	protected abstract CoreAbstEntityStepEJB<S> getStepEjb();
	protected abstract CoreAbstEntityStepLookup<S> getStepLookup();
	protected abstract CoreAbstEntityCstrEJB<C> getCstrEjb();
	protected abstract CoreAbstEntityCstrLookup<C> getCstrLookup();
	protected abstract CoreAbstBsnsObjBatch<E, I, H, J, S, C> getBatch();
}
