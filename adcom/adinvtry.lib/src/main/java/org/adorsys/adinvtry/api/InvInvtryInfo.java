package org.adorsys.adinvtry.api;

import org.adorsys.adinvtry.jpa.InvInvtry;


public class InvInvtryInfo {
	public static String prinInfo(InvInvtry invtry){
		return invtry.getBsnsObjNbr() + "_" + invtry.getTxStatus() + "_" + invtry.getBsnsPartner() + "_" + invtry.getPrchNetPrcPreTax();
	}
}
