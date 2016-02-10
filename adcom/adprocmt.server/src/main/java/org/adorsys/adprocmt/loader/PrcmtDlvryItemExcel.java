package org.adorsys.adprocmt.loader;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;

public class PrcmtDlvryItemExcel  extends PrcmtDlvryItem {
	private static final long serialVersionUID = 2831567647414124481L;
	
	public static final String DELIVERED_PREFIX = "d:";
	public static final String FREE_PREFIX = "f:";
	public static final String ENTRY_SEPARATOR = ",";
	public static final String STORED_PREFIX = "s:";
	public static final String QTY_START = "(";
	public static final String QTY_END = ")";
	

	/*
	 * A comma separated list of storage sections. strgSection(s:qtyStrd)
	 */
	private String strgSctns;
	
	/*
	 * A comma separated list of receiving org unit. rcvngOrgUnit(d:qtyDlvrd)(f:freeQty),rcvngOrgUnit(d:qtyDlvrd)(f:freeQty)
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
