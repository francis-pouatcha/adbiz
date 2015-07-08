package org.adorsys.adcatal.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstLangObject;

@Entity
@Description("CatalFamilyFeatMaping_description")
public class CatalFamilyFeatMaping extends CoreAbstLangObject {

	private static final long serialVersionUID = -2759458392635728552L;

	@Column
	@Description("CatalFamilyFeatMaping_familyName_description")
	private String familyName;
	@Column
	@Description("CatalFamilyFeatMaping_famPath_description")
	private String famPath;

	public String getFamilyName() {
		return this.familyName;
	}

	public void setFamilyName(final String familyName) {
		this.familyName = familyName;
	}

	public String getFamPath() {
		return famPath;
	}

	public void setFamPath(String famPath) {
		this.famPath = famPath;
	}
}