package org.adorsys.adcore.rest;

import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;

/**
 * Manages an archive file.
 * 
 * @author fpo
 */
public abstract class CoreAbstArchiveManager<E extends CoreAbstBsnsObject, EA extends CoreAbstBsnsObject, I extends CoreAbstBsnsItem, IA extends CoreAbstBsnsItem,
	H extends CoreAbstBsnsObjectHstry, HA extends CoreAbstBsnsObjectHstry, J extends CoreAbstEntityJob,
	S extends CoreAbstEntityStep, C extends CoreAbstEntityCstr> {

	protected abstract CoreAbstBsnsObjInjector<E, I, H, J, S, C> getInjector();	
	protected abstract CoreAbstIdentifiedEJB<EA> getArchiveObjEjb();
	protected abstract EA newArchiveObject();

	public void archive(String identif){
		E bnsObj = getInjector().getBsnsObjLookup().findByIdentif(identif);
		if(bnsObj==null) return;
		
		EA ea = newArchiveObject();
		ea.copyFrom(bnsObj);
		ea.setId(bnsObj.getId());
		getArchiveObjEjb().create(ea);
		
		// erase the business object
		getInjector().getBsnsObjEjb().archiveById(bnsObj.getId());
	}
}