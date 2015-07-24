package org.adorsys.adstock.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@MappedSuperclass
@Description("StkSection_description")
public class StkAbstractSection extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 1441848160823566249L;

	@Column
	@Description("StkSection_name_description")
	@NotNull
	private String name;

	@Column
	@Description("StkSection_position_description")
	private String position;

	@Column
	@Description("StkSection_geoCode_description")
	private String geoCode;
	
	@Column
	@Description("StkSection_wharehouse_description")
	private String wharehouse;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(final String position) {
		this.position = position;
	}

	public String getGeoCode() {
		return this.geoCode;
	}

	public void setGeoCode(final String geoCode) {
		this.geoCode = geoCode;
	}

	public String getWharehouse() {
		return wharehouse;
	}

	public void setWharehouse(String wharehouse) {
		this.wharehouse = wharehouse;
	}

	@Override
	protected String makeIdentif() {
		throw new IllegalStateException("Identifier is supposed to be set by caller.");
	}
}