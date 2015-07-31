package org.adorsys.adprocmt.trigger;

import javax.inject.Singleton;

import org.adorsys.adprocmt.api.PrcmtOrderHolder;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.adorsys.adprocmt.spi.dflt.ProcmtPOTriggerModeEnum;
/**
 * 
 * @author guymoyo
 *
 */
@Singleton
public class ManualTriggerModeHandler implements TriggerModeExecuter {

	public static final String key = ProcmtPOTriggerModeEnum.MANUAL.name();
	
	public static String getKey() {
		return key;
	}

	@Override
	public PrcmtOrderHolder executeTriggerMode(PrcmtProcOrder prcmtProcOrder) {
		// TODO implement manual trigger		
		if(prcmtProcOrder==null) return null;
		PrcmtOrderHolder prcmtProcOrderHolder = new PrcmtOrderHolder();
		prcmtProcOrderHolder.setPrcmtProcOrder(prcmtProcOrder);
		return prcmtProcOrderHolder;
	}

}
