package org.adorsys.adcatal.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@Entity
@Description("CatalArtEquivalence_description")
public class CatalArtEquivalence extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 7808069147519207704L;

	@Column
	@Description("CatalArtEquivalence_artEquivCode_description")
	@NotNull
	private String artEquivCode;

	@Column
	@Description("CatalArtEquivalence_mainArtIdentif_description")
	@NotNull
	private String mainArtIdentif;

	@Column
	@Description("CatalArtEquivalence_equivArtIdentif_description")
	@NotNull
	private String equivArtIdentif;

	@Column
	@Description("CatalArtEquivalence_usage_description")
	@Size(max=256)
	private String usage;

	public String getArtEquivCode() {
		return this.artEquivCode;
	}

	public void setArtEquivCode(final String artEquivCode) {
		this.artEquivCode = artEquivCode;
	}

	public String getMainArtIdentif() {
		return this.mainArtIdentif;
	}

	public void setMainArtIdentif(final String mainArtIdentif) {
		this.mainArtIdentif = mainArtIdentif;
	}

	public String getEquivArtIdentif() {
		return this.equivArtIdentif;
	}

	public void setEquivArtIdentif(final String equivArtIdentif) {
		this.equivArtIdentif = equivArtIdentif;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	@Override
	protected String makeIdentif() {
		artEquivCode = mainArtIdentif+"-"+equivArtIdentif;
		return artEquivCode;
	}
}