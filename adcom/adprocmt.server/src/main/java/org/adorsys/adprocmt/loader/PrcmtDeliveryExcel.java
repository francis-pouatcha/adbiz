package org.adorsys.adprocmt.loader;

import org.adorsys.adprocmt.jpa.PrcmtDelivery;

public class PrcmtDeliveryExcel extends PrcmtDelivery {
	private static final long serialVersionUID = -9117914013087417999L;

	private String rcvngOrgUnits;
	
	private String procmtOrderNbrs;

	public String getRcvngOrgUnits() {
		return rcvngOrgUnits;
	}

	public void setRcvngOrgUnits(String rcvngOrgUnits) {
		this.rcvngOrgUnits = rcvngOrgUnits;
	}

	public String getProcmtOrderNbrs() {
		return procmtOrderNbrs;
	}

	public void setProcmtOrderNbrs(String procmtOrderNbrs) {
		this.procmtOrderNbrs = procmtOrderNbrs;
	}
	
}
