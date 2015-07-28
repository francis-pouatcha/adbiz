package org.adorsys.adinvtry.loader;

import org.adorsys.adinvtry.jpa.InvAbstractInvtry;

public class InvInvtryExcel extends InvAbstractInvtry {

	private static final long serialVersionUID = -9207766763884043510L;

	private String rcvngOrgUnits;

	public String getRcvngOrgUnits() {
		return rcvngOrgUnits;
	}

	public void setRcvngOrgUnits(String rcvngOrgUnits) {
		this.rcvngOrgUnits = rcvngOrgUnits;
	}
}
