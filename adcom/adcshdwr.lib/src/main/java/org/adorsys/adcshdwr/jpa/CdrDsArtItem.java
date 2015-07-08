package org.adorsys.adcshdwr.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.jpa.CoreAbstTxctedItem;
import org.adorsys.javaext.description.Description;

@Entity
@Description("CdrDsArtItem_description")
public class CdrDsArtItem extends CoreAbstTxctedItem {
	private static final long serialVersionUID = -6398806516924684135L;
}