package org.adorsys.adprocmt.api;

import org.adorsys.adprocmt.jpa.PrcmtDelivery;

public class DeliveryInfo {
	public static String prinInfo(PrcmtDelivery delivery){
		return delivery.getBsnsObjNbr() + "_" + delivery.getTxStatus() + "_" + delivery.getBsnsPartner() + "_" + delivery.getPrchNetPrcTaxIncl();
	}
}
