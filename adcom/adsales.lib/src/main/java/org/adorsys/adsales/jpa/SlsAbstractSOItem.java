package org.adorsys.adsales.jpa;

import javax.persistence.MappedSuperclass;

import org.adorsys.adcore.jpa.CoreAbstTxctedItem;
import org.adorsys.javaext.description.Description;

@MappedSuperclass
@Description("SlsSOItem_description")
public abstract class SlsAbstractSOItem extends CoreAbstTxctedItem {
	private static final long serialVersionUID = -3951152520592548770L;
}