package org.adorsys.adsales.api;

import org.adorsys.adsales.jpa.SlsInvoice;

public class SlsInvoiceInfo {
	public static String prinInfo(SlsInvoice slsInvoice){
		return slsInvoice.getInvceNbr() + "_" + slsInvoice.getInvceStatus() + "_" + slsInvoice.getCreatingUsr()
				+ "_" + slsInvoice.getNetSPTaxIncl() + "-"+slsInvoice.getInvceDelivered()+"-"+slsInvoice.getInvcePymntStatus();
	}
}
