package org.adorsys.adcatal.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstLangObject;

@MappedSuperclass
public abstract class CatalAbstArtLangMap extends CoreAbstLangObject {

	private static final long serialVersionUID = -3492854713300991090L;

	@Column
	@Size(max=256)
	@Description("CatalAbstractFeatMapping_purpose_description")
	private String purpose;
	
	@Column
	@Description("CatalAbstractFeatMapping_usage_description")
	@Size(max=256)
	private String usage;
	
	@Column
	@Description("CatalAbstractFeatMapping_warnings_description")
	@Size(max=256)
	private String warnings;

	@Column
	@Description("CatalAbstractFeatMapping_substances_description")
	@Size(max=256)
	private String substances;

	@Column
	@Description("CatalArtFeatMapping_artName_description")
	private String artName;

	@Column
	@Description("CatalArtFeatMapping_shortName_description")
	private String shortName;
	
	public String getSubstances() {
		return this.substances;
	}

	public void setSubstances(final String substances) {
		this.substances = substances;
	}

	public String getUsage() {
		return this.usage;
	}

	public void setUsage(final String usage) {
		this.usage = usage;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getWarnings() {
		return warnings;
	}

	public void setWarnings(String warnings) {
		this.warnings = warnings;
	}

	public String getArtName() {
		return artName;
	}

	public void setArtName(String artName) {
		this.artName = artName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

}