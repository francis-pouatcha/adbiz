package org.adorsys.adbase.loader;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.xls.AbstractLoader;
import org.adorsys.adcore.xls.StepCallback;

@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DataSheetLoader extends AbstractLoader {
	
	@EJB
	private CorLdrStepCallback stepCallback;
	
	@Override
	public String getDir() {
		return getDataRoot() + "/adcom/adbase";
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}
}
