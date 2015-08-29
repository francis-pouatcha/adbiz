package org.adorsys.adbase.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@Entity 
@Table(name="BaseCountry")
@Description("Country_description")
public class Country extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 6524330877067168714L;

	@Column
	@Description("Country_iso2_description")
	@NotNull
	private String iso2;

	@Column
	@Description("Country_iso3_description")
	@NotNull
	private String iso3;

	@Column
	@Description("Country_name_description")
	private String name;

	@Column
	@Description("Country_dialCode_description")
	private String dialCode;

	@Column
	@Description("Country_telNbrSize_description")
	private BigDecimal telNbrSize;

	@Column
	@Description("Country_langsIso2_description")
	private String langsIso2;

	@Column
	@Description("Country_currsIso2_description")
	private String currsIso2;

	@Column
	@Description("Country_timeZones_description")
	private String timeZones;

	public String getIso2() {
		return this.iso2;
	}

	public void setIso2(final String iso2) {
		this.iso2 = iso2;
	}

	public String getIso3() {
		return this.iso3;
	}

	public void setIso3(final String iso3) {
		this.iso3 = iso3;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDialCode() {
		return this.dialCode;
	}

	public void setDialCode(final String dialCode) {
		this.dialCode = dialCode;
	}

	public BigDecimal getTelNbrSize() {
		return this.telNbrSize;
	}

	public void setTelNbrSize(final BigDecimal telNbrSize) {
		this.telNbrSize = telNbrSize;
	}

	public String getLangsIso2() {
		return this.langsIso2;
	}

	public void setLangsIso2(final String langsIso2) {
		this.langsIso2 = langsIso2;
	}

	public String getCurrsIso2() {
		return this.currsIso2;
	}

	public void setCurrsIso2(final String currsIso2) {
		this.currsIso2 = currsIso2;
	}

	public String getTimeZones() {
		return this.timeZones;
	}

	public void setTimeZones(final String timeZones) {
		this.timeZones = timeZones;
	}

	@Override
	protected String makeIdentif() {
		return iso3;
	}
}