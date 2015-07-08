package org.adorsys.adprocmt.api;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2POItem;

public class PrcmtDlvryItem2PoItemHolder {
	private PrcmtDlvryItem2POItem poItem;
	private boolean deleted;

	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public PrcmtDlvryItem2POItem getPoItem() {
		return poItem;
	}
	public void setPoItem(PrcmtDlvryItem2POItem poItem) {
		this.poItem = poItem;
	}
}
