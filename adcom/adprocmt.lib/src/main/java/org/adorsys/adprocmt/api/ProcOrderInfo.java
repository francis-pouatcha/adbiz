package org.adorsys.adprocmt.api;

import org.adorsys.adprocmt.jpa.PrcmtProcOrder;

public class ProcOrderInfo {
	public static String prinInfo(PrcmtProcOrder procOrder){
		return procOrder.getBsnsObjNbr() + "_" + procOrder.getTxStatus() + "_" + procOrder.getBsnsPartner() + "_" + procOrder.getPrchNetPrcTaxIncl();
	}
}
