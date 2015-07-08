package org.adorsys.adcore.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class CoreAbstBsnsObjectHstry extends CoreAbstIdentifObjectHstry {
	private static final long serialVersionUID = -1901201064736563160L;

	@Column
	private String itemIdentif;

	public String getItemIdentif() {
		return itemIdentif;
	}

	public void setItemIdentif(String itemIdentif) {
		this.itemIdentif = itemIdentif;
	}
}