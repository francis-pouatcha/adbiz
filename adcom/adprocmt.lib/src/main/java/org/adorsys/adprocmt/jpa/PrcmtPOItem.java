package org.adorsys.adprocmt.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstTxctedItem;

@Entity
@Description("PrcmtPOItem_description")
public class PrcmtPOItem extends CoreAbstTxctedItem {
	private static final long serialVersionUID = -7855434200281666889L;

}