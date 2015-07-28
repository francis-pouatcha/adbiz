package org.adorsys.adcshdwr.api;

import org.adorsys.adcshdwr.jpa.CdrDrctSales;


public class CdrDrctSalesInfo {
	public static String prinInfo(CdrDrctSales ds){
		return ds.getBsnsObjNbr() + "_" + ds.getTxStatus() + "_" + ds.getBsnsPartner() + "_" + ds.getSlsNetPrcTaxIncl();
	}
}
