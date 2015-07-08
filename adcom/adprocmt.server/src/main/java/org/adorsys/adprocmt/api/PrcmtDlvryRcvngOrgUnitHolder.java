package org.adorsys.adprocmt.api;

import org.adorsys.adprocmt.jpa.PrcmtDlvry2Ou;

public class PrcmtDlvryRcvngOrgUnitHolder {

	private PrcmtDlvry2Ou rcvngOrgUnit;
	
	private boolean deleted;

	public PrcmtDlvry2Ou getRcvngOrgUnit() {
		return rcvngOrgUnit;
	}

	public void setRcvngOrgUnit(PrcmtDlvry2Ou rcvngOrgUnit) {
		this.rcvngOrgUnit = rcvngOrgUnit;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
