package org.adorsys.adcatal.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.adorsys.adcore.annotation.Description;

@Entity
@Description("CatalFamilyFeatMaping_description")
public class CatalProdFmlyLangMap extends CatalAbstArtLangMap {

	private static final long serialVersionUID = -2759458392635728552L;

	@Column
	@Description("CatalFamilyFeatMaping_famPath_description")
	private String famPath;

	public String getFamPath() {
		return famPath;
	}

	public void setFamPath(String famPath) {
		this.famPath = famPath;
	}
}