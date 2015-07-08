package org.adorsys.adsales.jpa;

import javax.persistence.MappedSuperclass;

import org.adorsys.adcore.jpa.CoreAbstTxctedItem;
import org.adorsys.javaext.description.Description;

@MappedSuperclass
@Description("SlsDlvryItem_description")
public abstract class SlsAbstractDlvryItem extends CoreAbstTxctedItem {
	private static final long serialVersionUID = 7028588534717897469L;
}