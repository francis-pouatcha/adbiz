package org.adorsys.adsales.api;

import org.adorsys.adsales.jpa.SlsSalesOrder;

public class SaleOrderInfo {
	public static String prinInfo(SlsSalesOrder slsSalesOrder){
		return slsSalesOrder.getSoNbr() + "_" + slsSalesOrder.getSoStatus().name() + "_" + slsSalesOrder.getAcsngUser() + "_" + slsSalesOrder.getNetSPTaxIncl();
	}
}
