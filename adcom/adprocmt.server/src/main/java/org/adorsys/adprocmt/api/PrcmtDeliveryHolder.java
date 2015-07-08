package org.adorsys.adprocmt.api;

import java.util.ArrayList;
import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtDelivery;

/**
 * The delivery holder carries delivery objects between server and client applciations.
 * 
 * @author francis
 *
 */
public class PrcmtDeliveryHolder {

	private PrcmtDelivery delivery;
	
	private List<PrcmtDeliveryItemHolder> deliveryItems = new ArrayList<PrcmtDeliveryItemHolder>();
	
	/*
	 * A partial list of procurement order involved in this delivery. Can be used to help associate
	 * delivery items with procurement order items.
	 */
	private List<PrcmtOrderNbrHolder> procmtOrderNbrs = new ArrayList<PrcmtOrderNbrHolder>();
	
	/*
	 * a partial list of receiving organization units. Generally associated with a percentage
	 * of good. If nothing else is specified, good are shared among organization unit 
	 * according to the percentage defined here.
	 */
	private List<PrcmtDlvryRcvngOrgUnitHolder> rcvngOrgUnits = new ArrayList<PrcmtDlvryRcvngOrgUnitHolder>(); 

	public PrcmtDelivery getDelivery() {
		return delivery;
	}

	public void setDelivery(PrcmtDelivery delivery) {
		this.delivery = delivery;
	}

	public List<PrcmtDeliveryItemHolder> getDeliveryItems() {
		return deliveryItems;
	}

	public void setDeliveryItems(List<PrcmtDeliveryItemHolder> deliveryItems) {
		this.deliveryItems = deliveryItems;
	}

	public List<PrcmtDlvryRcvngOrgUnitHolder> getRcvngOrgUnits() {
		return rcvngOrgUnits;
	}

	public void setRcvngOrgUnits(List<PrcmtDlvryRcvngOrgUnitHolder> rcvngOrgUnits) {
		this.rcvngOrgUnits = rcvngOrgUnits;
	}

	public List<PrcmtOrderNbrHolder> getProcmtOrderNbrs() {
		return procmtOrderNbrs;
	}

	public void setProcmtOrderNbrs(List<PrcmtOrderNbrHolder> procmtOrderNbrs) {
		this.procmtOrderNbrs = procmtOrderNbrs;
	}
}
