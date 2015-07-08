package org.adorsys.adcatal.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstLangObject;

@Entity
@Description("CatalArt2ProductFamily_description")
public class CatalArt2ProductFamily extends CoreAbstLangObject {
	private static final long serialVersionUID = 1621931613925203539L;

	@Column
	@Description("CatalArt2ProductFamily_artPic_description")
	@NotNull
	private String artPic;
	
	@Column
	@Description("CatalArt2ProductFamily_famCode_description")
	@NotNull
	private String famCode;

	@Column
	@Description("CatalProductFamily_famPath_description")
	private String famPath;
	
	@Column
	@Description("CatalArt2ProductFamily_familyName_description")
	@NotNull
	private String familyName;
	
	public String getArtPic() {
		return artPic;
	}

	public void setArtPic(String artPic) {
		this.artPic = artPic;
	}

	public String getFamCode() {
		return famCode;
	}

	public void setFamCode(String famCode) {
		this.famCode = famCode;
	}

	public String getFamPath() {
		return famPath;
	}

	public void setFamPath(String famPath) {
		this.famPath = famPath;
	}

	@Override
	protected String makeIdentif() {
		return toIdentif(famCode, artPic, getLangIso2());
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public static final String toIdentif(String famCode, String artPic, String langIso2){
		return famCode + "_" + artPic + "_" + langIso2;
	}
}