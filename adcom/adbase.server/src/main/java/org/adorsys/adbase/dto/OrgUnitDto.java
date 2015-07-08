/**
 * 
 */
package org.adorsys.adbase.dto;

import java.util.Date;

import org.adorsys.adbase.jpa.OrgUnit;

/**
 * @author boriswaguia
 *
 */
public class OrgUnitDto {
	
	private String id;
	private String identif;
	private String fullName;
	private String shortName;
	private String typeIdentif;
	private String typeName;
	private String ctrIso3;
	private String countryName;
	private String parentIdentif;
	private Date validFrom;
	private Date validTo;
	
	public OrgUnitDto() {
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdentif() {
		return identif;
	}
	public void setIdentif(String identif) {
		this.identif = identif;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getTypeIdentif() {
		return typeIdentif;
	}
	public void setTypeIdentif(String typeIdentif) {
		this.typeIdentif = typeIdentif;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getCtrIso3() {
		return ctrIso3;
	}
	public void setCtrIso3(String ctrIso3) {
		this.ctrIso3 = ctrIso3;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public Date getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}
	public String getParentIdentif() {
		return parentIdentif;
	}
	public void setParentIdentif(String parentIdentif) {
		this.parentIdentif = parentIdentif;
	}
	public Date getValidTo() {
		return validTo;
	}
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}


	public static OrgUnitDto createDto(OrgUnit orgUnit, String ctryIso3, String typeName,
			String ctrName) {
		OrgUnitDto dto = new OrgUnitDto();
		dto.setId(orgUnit.getId());
		dto.setCountryName(ctrName);
		dto.setCtrIso3(ctryIso3);
		dto.setFullName(orgUnit.getFullName());
		dto.setIdentif(orgUnit.getIdentif());
		dto.setShortName(orgUnit.getShortName());
		dto.setTypeIdentif(orgUnit.getTypeIdentif());
		dto.setTypeName(typeName);
		dto.setValidFrom(orgUnit.getValidFrom());
		return dto;
	}
	
	public OrgUnit toOrgUnit() {
		OrgUnit ou = new OrgUnit();
		ou.setCtryIso3(ctrIso3);
		ou.setFullName(fullName);
		ou.setIdentif(identif);
		ou.setShortName(shortName);
		ou.setTypeIdentif(typeIdentif);
		ou.setValidFrom(validFrom);
		ou.setValidTo(validTo);
		return ou;
	}
	
	@Override
	public String toString() {
		return "OrgUnitDto [id=" + id + ", identif=" + identif + ", fullName="
				+ fullName + ", shortName=" + shortName + ", typeIdentif="
				+ typeIdentif + ", typeName=" + typeName + ", ctrIso3="
				+ ctrIso3 + ", countryName=" + countryName + ", parentIdentif="
				+ parentIdentif + ", validFrom=" + validFrom + "]";
	}
	
}
