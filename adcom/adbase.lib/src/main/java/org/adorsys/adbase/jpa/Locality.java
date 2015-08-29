package org.adorsys.adbase.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@Entity 
@Table(name="BaseLocality")
@Description("Locality_description")
public class Locality extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 4915533373959238528L;

	@Column
	@Description("Locality_ouIdentif_description")
	@NotNull
	private String ouIdentif;

	@Column
	@Description("Locality_langIso2_description")
	@NotNull
	private String langIso2;

	@Column
	@Description("Locality_ctryISO3_description")
	@NotNull
	private String ctryISO3;

	@Column
	@Description("Locality_region_description")
	private String region;

	@Column
	@Description("Locality_city_description")
	private String city;

	@Column
	@Description("Locality_locStr_description")
	@NotNull
	private String locStr;

	@Column
	@Description("Locality_hoursOfOp_description")
	@Size(max = 256)
	private String hoursOfOp;

	@Column
	@Description("Locality_timeZone_description")
	@NotNull
	private String timeZone;

	public String getOuIdentif() {
		return this.ouIdentif;
	}

	public void setOuIdentif(final String ouIdentif) {
		this.ouIdentif = ouIdentif;
	}

	public String getLangIso2() {
		return this.langIso2;
	}

	public void setLangIso2(final String langIso2) {
		this.langIso2 = langIso2;
	}

	public String getCtryISO3() {
		return this.ctryISO3;
	}

	public void setCtryISO3(final String ctryISO3) {
		this.ctryISO3 = ctryISO3;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(final String region) {
		this.region = region;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getLocStr() {
		return this.locStr;
	}

	public void setLocStr(final String locStr) {
		this.locStr = locStr;
	}

	public String getHoursOfOp() {
		return this.hoursOfOp;
	}

	public void setHoursOfOp(final String hoursOfOp) {
		this.hoursOfOp = hoursOfOp;
	}

	public String getTimeZone() {
		return this.timeZone;
	}

	public void setTimeZone(final String timeZone) {
		this.timeZone = timeZone;
	}

	@Override
	protected String makeIdentif() {
		return ouIdentif + "_" + langIso2;
	}
}