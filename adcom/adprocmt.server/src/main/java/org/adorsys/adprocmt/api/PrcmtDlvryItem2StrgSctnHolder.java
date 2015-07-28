package org.adorsys.adprocmt.api;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctn;

public class PrcmtDlvryItem2StrgSctnHolder {

	private PrcmtDlvryItem2StrgSctn strgSctn;
	
	private boolean deleted;

	public PrcmtDlvryItem2StrgSctn getStrgSctn() {
		return strgSctn;
	}

	public void setStrgSctn(PrcmtDlvryItem2StrgSctn strgSctn) {
		this.strgSctn = strgSctn;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
