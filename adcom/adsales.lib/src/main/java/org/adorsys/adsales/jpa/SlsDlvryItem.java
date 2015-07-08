package org.adorsys.adsales.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemHeader;
import org.adorsys.javaext.description.Description;

@Entity
@Description("PrcmtDlvryItem_description")
public class SlsDlvryItem extends SlsAbstractDlvryItem {
	private static final long serialVersionUID = 6727828567847544995L;

	public void fillDataFromOrderItem(SlsSOItem soItem) {
		soItem.copyTo(this);
		CoreAbstBsnsItemHeader cleaner = new CoreAbstBsnsItemHeader();
		cleaner.copyTo(this);
	}

	public void fillDataFromInvoiceItem(SlsInvceItem invoiceItem) {
		invoiceItem.copyTo(this);
		CoreAbstBsnsItemHeader cleaner = new CoreAbstBsnsItemHeader();
		cleaner.copyTo(this);
	}
}