package org.adorsys.adprocmt.api;

import java.util.ArrayList;
import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;

public class PrcmtDeliveryItemHolder {

	private PrcmtDlvryItem dlvryItem;
	
	private List<PrcmtDlvryItem2PoItemHolder> poItems = new ArrayList<PrcmtDlvryItem2PoItemHolder>();
	
	private List<PrcmtDlvryItem2RcvngOrgUnitHolder> recvngOus = new ArrayList<PrcmtDlvryItem2RcvngOrgUnitHolder>();
	
	private List<PrcmtDlvryItem2StrgSctnHolder> strgSctns = new ArrayList<PrcmtDlvryItem2StrgSctnHolder>();
	
	private boolean deleted;

	public PrcmtDlvryItem getDlvryItem() {
		return dlvryItem;
	}

	public void setDlvryItem(PrcmtDlvryItem dlvryItem) {
		this.dlvryItem = dlvryItem;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public List<PrcmtDlvryItem2PoItemHolder> getPoItems() {
		return poItems;
	}

	public void setPoItems(List<PrcmtDlvryItem2PoItemHolder> poItems) {
		this.poItems = poItems;
	}

	public List<PrcmtDlvryItem2RcvngOrgUnitHolder> getRecvngOus() {
		return recvngOus;
	}

	public void setRecvngOus(List<PrcmtDlvryItem2RcvngOrgUnitHolder> recvngOus) {
		this.recvngOus = recvngOus;
	}

	public List<PrcmtDlvryItem2StrgSctnHolder> getStrgSctns() {
		return strgSctns;
	}

	public void setStrgSctns(List<PrcmtDlvryItem2StrgSctnHolder> strgSctns) {
		this.strgSctns = strgSctns;
	}
}
