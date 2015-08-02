package org.adorsys.adcore.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import org.apache.commons.lang3.StringUtils;

@MappedSuperclass
public abstract class CoreAbstBsnsObjectHstry extends CoreAbstIdentifHstry {
	private static final long serialVersionUID = -1901201064736563160L;

	@Column
	private String itemIdentif;

	@PrePersist
	public void prePersist(){
		if(StringUtils.isBlank(getEntIdentif()) && StringUtils.isNotBlank(itemIdentif))
			setEntIdentif(itemIdentif);
		if(StringUtils.isBlank(getCntnrIdentif()) && StringUtils.isNotBlank(getEntIdentif()))
			setCntnrIdentif(getEntIdentif());
		super.prePersist();
	}
	
	public String getItemIdentif() {
		return itemIdentif;
	}

	public void setItemIdentif(String itemIdentif) {
		this.itemIdentif = itemIdentif;
	}
}