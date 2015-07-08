package org.adorsys.adprocmt.api;

import java.util.ArrayList;
import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtProcOrder;

public class PrcmtOrderHolder {
	
	private PrcmtProcOrder prcmtProcOrder;
	
	private List<PrcmtOrderItemHolder> poItems = new ArrayList<PrcmtOrderItemHolder>();

	public List<PrcmtOrderItemHolder> getPoItems() {
		return poItems;
	}

	public void setPoItems(List<PrcmtOrderItemHolder> poItems) {
		this.poItems = poItems;
	}

	public PrcmtProcOrder getPrcmtProcOrder() {
		return prcmtProcOrder;
	}

	public void setPrcmtProcOrder(PrcmtProcOrder prcmtProcOrder) {
		this.prcmtProcOrder = prcmtProcOrder;
	}

}
