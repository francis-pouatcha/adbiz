package org.adorsys.adcore.rest;

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


	public abstract CoreAbstBsnsObjectLookup<E> getBsnsObjLookup();
	public abstract CoreAbstBsnsObjectEJB<E, I, H, J, S, C> getBsnsObjEjb();
	public abstract CoreAbstBsnsItemLookup<I> getItemLookup();
	public abstract CoreAbstBsnsItemEJB<E, I, H, J, S, C> getItemEjb();
	public abstract CoreAbstBsnsObjectHstryLookup<H> getHstrLookup();
	public abstract CoreAbstBsnsObjectHstryEJB<E, I, H, J, S, C> getHstrEjb();
	public abstract String getSequenceGeneratorPrefix();
	public abstract CoreAbstEntityJobEJB<J> getJobEjb();
	public abstract CoreAbstEntityJobLookup<J> getJobLookup();
	public abstract CoreAbstEntityStepEJB<S> getStepEjb();
	public abstract CoreAbstEntityStepLookup<S> getStepLookup();
	public abstract CoreAbstEntityCstrEJB<C> getCstrEjb();
	public abstract CoreAbstEntityCstrLookup<C> getCstrLookup();
}
