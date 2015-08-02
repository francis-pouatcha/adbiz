package org.adorsys.adcatal.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@Entity
@Description("CatalArt2ProductFamily_description")
public class CatalArt2ProductFamily extends CoreAbstIdentifObject {
	private static final long serialVersionUID = 4180486511319651103L;

	@Column
	@Description("CatalArt2ProductFamily_artPic_description")
	@NotNull
	private String artPic;
	
	@Column
	@Description("CatalArt2ProductFamily_famCode_description")
	@NotNull
	private String famCode;

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

	@Override
	protected String makeIdentif() {
		return toIdentif(famCode, artPic);
	}

	public static final String toIdentif(String famCode, String artPic){
		return famCode + "_" + artPic;
	}
}