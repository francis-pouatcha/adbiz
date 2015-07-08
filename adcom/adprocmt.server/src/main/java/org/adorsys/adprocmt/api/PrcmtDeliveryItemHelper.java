package org.adorsys.adprocmt.api;

import java.util.ArrayList;
import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;

/**
 * The item helper helps group items by identifiers. We might have many lines of the same
 * item in an order. In this case we will have to group them first befoe we can handle them.
 * 
 * @author francis
 *
 */
public class PrcmtDeliveryItemHelper {

	private List<PrcmtDeliveryItemHolder> incomingItems = new ArrayList<PrcmtDeliveryItemHolder>();
	
	private String itemIdentif;
	
	private String dlvryNbr;
	
	private String artPic;
	
	private String rcvngOrgUnit;
	
	private String lotPic;
	
	private PrcmtDlvryItem persistentItem;

	public String getItemIdentif() {
		return itemIdentif;
	}

	public void setItemIdentif(String itemIdentif) {
		this.itemIdentif = itemIdentif;
	}

	public PrcmtDlvryItem getPersistentItem() {
		return persistentItem;
	}

	public void setPersistentItem(PrcmtDlvryItem persistentItem) {
		this.persistentItem = persistentItem;
	}

	public List<PrcmtDeliveryItemHolder> getIncomingItems() {
		return incomingItems;
	}

	public void setIncomingItems(List<PrcmtDeliveryItemHolder> incomingItems) {
		this.incomingItems = incomingItems;
	}
}
