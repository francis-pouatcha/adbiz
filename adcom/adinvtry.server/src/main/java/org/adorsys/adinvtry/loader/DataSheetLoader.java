package org.adorsys.adinvtry.loader;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.adorsys.adcore.xls.AbstractLoader;

@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Startup
public class DataSheetLoader extends AbstractLoader {
	String dataDir = "adcom/adinvtry/data";

	@Override
	public String getDir() {
		return dataDir;
	}
	
	@Override
	public String getFileName() {
		return "adinvtry.tpl.xls";
	}
}
