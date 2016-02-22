package org.adorsys.adcore.xls;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public abstract class CoreAbstEntityLoader<T extends CoreAbstIdentifObject> extends CoreAbstLoader<T>{

	protected abstract CoreAbstIdentifLookup<T> getLookup();
	protected abstract CoreAbstIdentifiedEJB<T> getEjb();
	
	@Override
	public T save(T entity, List<PropertyDesc> fields){
		String identif = entity.getIdentif();
		T found = getLookup().findByIdentif(identif);
		if(found!=null){
			if(!objectEquals(found, entity, fields)){
				entity.setId(found.getId());
				return getEjb().update(found);
			} else {
				return found;
			}
		} else {
			return getEjb().create(entity);
		}
	}
}
