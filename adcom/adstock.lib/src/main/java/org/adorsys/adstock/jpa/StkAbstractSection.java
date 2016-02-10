package org.adorsys.adstock.jpa;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.apache.commons.lang3.StringUtils;

/**
 * The container of this section is the cntnrIdentif
 * 
 * @author fpo
 *
 */
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
	
	@Enumerated(EnumType.STRING)
	private StkSectionType sectionType;
	
	/*
	 * The path to the root section.
	 */
	private String path;

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public StkSectionType getSectionType() {
		return sectionType;
	}

	public void setSectionType(StkSectionType sectionType) {
		this.sectionType = sectionType;
	}

	@Override
	protected String makeIdentif() {
		throw new IllegalStateException("Identifier is supposed to be set by caller.");
	}
	
	public boolean isChildOf(String parentCode){
		if(StringUtils.isBlank(parentCode)) return true;
		if(StringUtils.isBlank(path)) return true;// root
		return StringUtils.containsIgnoreCase(path, makePathComponent(parentCode));
	}
	
	public String makePathComponent(String code){
		return "(" + code + ")";
	}
}