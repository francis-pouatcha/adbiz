package org.adorsys.adcore.rest;

import java.util.List;

import javax.enterprise.event.Observes;

import org.adorsys.adcore.event.EntityDeletedEvent;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

/**
 * Allows the deletion of a contained object when the container object is deleted.
 * 
 * @author francis
 *
 * @param <C>
 * @param <E>
 */
public abstract class CoreAbstContainerDeletedLstnr<C extends CoreAbstIdentifObject, E extends CoreAbstIdentifObject> {

	protected abstract CoreAbstIdentifiedEJB<E> getEltEjb();
	
	protected abstract CoreAbstIdentifLookup<E> getEltLookup();
	
	public void handleEntityDeletedEvent(@Observes @EntityDeletedEvent C cntnr){
		String cntnrIdentif = cntnr.getIdentif();
		int count = getEltLookup().countByCntnrIdentif(cntnrIdentif).intValue();
		List<E> found = getEltLookup().findByCntnrIdentif(cntnrIdentif, 0, count);
		for (E elt : found) {
			getEltEjb().deleteById(elt.getId());
		}
	}
	
}
