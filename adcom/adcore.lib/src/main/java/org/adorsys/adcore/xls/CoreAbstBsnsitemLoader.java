package org.adorsys.adcore.xls;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.adorsys.adcore.exceptions.AdRestException;
import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;

@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public abstract class CoreAbstBsnsitemLoader<E extends CoreAbstBsnsObject, I extends CoreAbstBsnsItem, 
	H extends CoreAbstBsnsObjectHstry, J extends CoreAbstEntityJob,
	S extends CoreAbstEntityStep, C extends CoreAbstEntityCstr, SI extends CoreAbstBsnsObjectSearchInput<E>> extends CoreAbstLoader<I>{

	protected abstract CoreAbstBsnsObjectManager<E, I, H, J, S, C, CoreAbstBsnsObjectSearchInput<E>> getManager();
	protected abstract CoreAbstBsnsObjectLookup<E> getLookup();
	
	@Override
	public I save(I entity, List<PropertyDesc> fields){
		try {
			return getManager().addItem(entity.getCntnrIdentif(), entity);
		} catch (AdRestException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void done(I last) {
		
		if(last==null || last.getCntnrIdentif()==null) return;
		
		String identif = last.getCntnrIdentif();
		
		Long count = getLookup().countByIdentif(identif);
		if(count<=0) return;

		try {
			getManager().close(identif);
			getManager().post(identif);
		} catch (AdRestException e) {
			throw new IllegalStateException(e);
		}
		
	}
}
