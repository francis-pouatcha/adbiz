package org.adorsys.adprocmt.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemHeader;
import org.adorsys.adcore.jpa.CoreAbstTxctedItem;
import org.adorsys.javaext.description.Description;

@Entity
@Description("PrcmtDlvryItem_description")
public class PrcmtDlvryItem extends CoreAbstTxctedItem {
	private static final long serialVersionUID = 6727828567847544995L;

	public void fillDataFromOrderItem(PrcmtPOItem poItem) {
		poItem.copyTo(this);
		new CoreAbstBsnsItemHeader().copyTo(this);// cleaup header info.
	}
}