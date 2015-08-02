package org.adorsys.adcatal.rest.integ.utils;

import java.util.List;

import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;

public abstract class TestSearchInput<E extends CoreAbstIdentifObject, SI extends CoreAbstIdentifObjectSearchInput<E>> {
	
	protected abstract SI newSearchInput();
	
	protected abstract E getEntity();
	
	
	
	public CoreAbstIdentifObjectSearchInput<E> createTestSearchInput(List<String> fieldNames, Class<? extends E> clazz, String identif, String valueDt){
		
		  CoreAbstIdentifObjectSearchInput<E> searchInput = newSearchInput();
		  searchInput.setStart(0);
		  searchInput.setMax(10);
		  searchInput.setClassName(clazz.getClass().getName());
		  searchInput.setEntity(getEntity());
		  searchInput.setIdentifFrom(identif);
		  searchInput.setIdentifTo(identif);
		  searchInput.setValueDtFrom(valueDt);
		  searchInput.setValueDtTo(valueDt);
		  searchInput.setFieldNames(fieldNames);
		return searchInput;
	}
	
	

}
