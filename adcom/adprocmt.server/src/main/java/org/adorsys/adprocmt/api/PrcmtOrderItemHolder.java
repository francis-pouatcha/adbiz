package org.adorsys.adprocmt.api;

import org.adorsys.adprocmt.jpa.PrcmtPOItem;

public class PrcmtOrderItemHolder {
	
	private PrcmtPOItem prcmtPOItem;
	
	private boolean deleted;

	public PrcmtPOItem getPrcmtPOItem() {
		return prcmtPOItem;
	}

	public void setPrcmtPOItem(PrcmtPOItem prcmtPOItem) {
		this.prcmtPOItem = prcmtPOItem;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
