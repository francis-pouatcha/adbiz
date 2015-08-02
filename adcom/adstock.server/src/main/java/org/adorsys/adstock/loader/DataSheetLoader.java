package org.adorsys.adstock.loader;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.adorsys.adcore.xls.AbstractLoader;

@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Startup
public class DataSheetLoader extends AbstractLoader {

	String dataDir = "adcom/adstock/data";

	@Override
	public String getDir() {
		return dataDir;
	}
	@Override
	public String getFileName() {
		return "adstock.tpl.xls";
	}

}
