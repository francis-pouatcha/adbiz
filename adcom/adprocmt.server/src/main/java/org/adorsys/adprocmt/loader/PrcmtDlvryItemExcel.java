package org.adorsys.adprocmt.loader;

public class PrcmtDlvryItemExcel {

	/*
	 * A comma separated list of storage sections. strgSection:qtyStrd
	 */
	private String strgSctns;
	
	/*
	 * A comma separated list of receiving org unit. rcvngOrgUnit:qtyDlvrd:freeQty
	 */
	private String recvngOus;

	public String getStrgSctns() {
		return strgSctns;
	}

	public void setStrgSctns(String strgSctns) {
		this.strgSctns = strgSctns;
	}

	public String getRecvngOus() {
		return recvngOus;
	}

	public void setRecvngOus(String recvngOus) {
		this.recvngOus = recvngOus;
	}
	
}
