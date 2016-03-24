package org.adorsys.adprocmt.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstTxctedItem;

@Entity
@Description("PrcmtDlvryItem_description")
public class PrcmtDlvryItemArchive extends CoreAbstTxctedItem {
	private static final long serialVersionUID = 1957862272633623038L;
}