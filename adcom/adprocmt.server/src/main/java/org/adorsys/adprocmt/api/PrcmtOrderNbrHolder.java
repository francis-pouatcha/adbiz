package org.adorsys.adprocmt.api;

public class PrcmtOrderNbrHolder {
	private String procmtOrderNbr;
	private boolean deleted;
	public String getProcmtOrderNbr() {
		return procmtOrderNbr;
	}
	public void setProcmtOrderNbr(String procmtOrderNbr) {
		this.procmtOrderNbr = procmtOrderNbr;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
