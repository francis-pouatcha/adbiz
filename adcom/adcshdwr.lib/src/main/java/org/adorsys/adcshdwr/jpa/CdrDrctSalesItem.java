package org.adorsys.adcshdwr.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstTxctedItem;

@Entity
@Description("CdrDrctSalesItem_description")
public class CdrDrctSalesItem extends CoreAbstTxctedItem {
	private static final long serialVersionUID = -6398806516924684135L;
}