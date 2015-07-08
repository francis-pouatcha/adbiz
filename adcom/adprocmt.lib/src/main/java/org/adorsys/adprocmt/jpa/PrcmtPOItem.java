package org.adorsys.adprocmt.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.jpa.CoreAbstTxctedItem;
import org.adorsys.javaext.description.Description;

@Entity
@Description("PrcmtPOItem_description")
public class PrcmtPOItem extends CoreAbstTxctedItem {
	private static final long serialVersionUID = -7855434200281666889L;

}