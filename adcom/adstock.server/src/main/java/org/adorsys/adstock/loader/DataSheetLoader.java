package org.adorsys.adstock.loader;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.xls.AbstractLoader;
import org.adorsys.adcore.xls.StepCallback;

@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Startup
public class DataSheetLoader extends AbstractLoader {

	String dataDir = "adcom/adstock/data";

	@EJB
	private CorLdrStepCallback stepCallback;
	
	@Override
	public String getDir() {
		return dataDir;
	}
	@Override
	public String getFileName() {
		return "adstock.tpl.xls";
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}
}
