package org.adorsys.adcshdwr.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstTxctedItem;

@Entity
@Description("CdrDrctSalesItem_description")
public class CdrDrctSalesItemArchive extends CoreAbstTxctedItem {
	private static final long serialVersionUID = 5819477501512127281L;
}