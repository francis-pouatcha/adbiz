package org.adorsys.adprocmt.api;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2Ou;

public class PrcmtDlvryItem2RcvngOrgUnitHolder {

	private PrcmtDlvryItem2Ou rcvngOrgUnit;
	
	private boolean deleted;

	public PrcmtDlvryItem2Ou getRcvngOrgUnit() {
		return rcvngOrgUnit;
	}

	public void setRcvngOrgUnit(PrcmtDlvryItem2Ou rcvngOrgUnit) {
		this.rcvngOrgUnit = rcvngOrgUnit;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
