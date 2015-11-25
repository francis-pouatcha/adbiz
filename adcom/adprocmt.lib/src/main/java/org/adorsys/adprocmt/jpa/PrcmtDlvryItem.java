package org.adorsys.adprocmt.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstTxctedItem;

@Entity
@Description("PrcmtDlvryItem_description")
public class PrcmtDlvryItem extends CoreAbstTxctedItem {
	private static final long serialVersionUID = 6727828567847544995L;

	public void fillDataFromOrderItem(PrcmtPOItem poItem) {
		poItem.copyTo(this);
		resetHeader();
	}
}