package org.adorsys.adbase.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.annotation.Description;

@Entity
@Description("BaseCountryName_description")
public class BaseCountryName extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 2066333794336871081L;

	@Column
	@Description("BaseCountryName_iso2_description")
	@NotNull
	private String iso2;

	@Column
	@Description("BaseCountryName_iso3_description")
	@NotNull
	private String iso3;

	@Column
	@Description("BaseCountryName_langsIso2_description")
	@NotNull
	private String langsIso2;

	@Column
	@Description("BaseCountryName_name_description")
	@NotNull
	private String name;
	
	@Override
	protected String makeIdentif() {
		return toIdentif(iso3, langsIso2);
	}
	public static String toIdentif(String iso3, String langsIso2) {
		return iso3 + "_" + langsIso2;
	}

	public String getIso2() {
		return iso2;
	}

	public void setIso2(String iso2) {
		this.iso2 = iso2;
	}

	public String getIso3() {
		return iso3;
	}

	public void setIso3(String iso3) {
		this.iso3 = iso3;
	}

	public String getLangsIso2() {
		return langsIso2;
	}

	public void setLangsIso2(String langsIso2) {
		this.langsIso2 = langsIso2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
