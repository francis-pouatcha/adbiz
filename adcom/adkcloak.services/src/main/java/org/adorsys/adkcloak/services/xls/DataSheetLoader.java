package org.adorsys.adkcloak.services.xls;

import org.adorsys.adcore.xls.AbstractLoader;
import org.adorsys.adcore.xls.StepCallback;

public class DataSheetLoader extends AbstractLoader {
	
	@Override
	public String getDir() {
		return getDataRoot();
	}

	@Override
	public String getSuffix() {
		return "keycloak.xls";
	}

	@Override
	protected StepCallback getStepCallback() {
		// TODO Auto-generated method stub
		return null;
	}
}
