package org.adorsys.adcore.xls;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;

@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public abstract class CoreAbstBsnsObjectLoader<E extends CoreAbstBsnsObject, I extends CoreAbstBsnsItem, 
	H extends CoreAbstBsnsObjectHstry, J extends CoreAbstEntityJob,
	S extends CoreAbstEntityStep, C extends CoreAbstEntityCstr, SI extends CoreAbstBsnsObjectSearchInput<E>> extends CoreAbstLoader<E>{

	protected abstract CoreAbstBsnsObjectManager<E, I, H, J, S, C, CoreAbstBsnsObjectSearchInput<E>> getManager();
	
	@Override
	public E save(E entity, List<PropertyDesc> fields){
		return getManager().initiateBsnsObj(entity);
	}
}
