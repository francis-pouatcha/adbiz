package org.adorsys.adbase.jpa;

import java.io.Serializable;

/**
 * This describes the identifier of a org unit.
 * 
 * @author francis
 *
 */
public class OrgUnitId implements Serializable {
	private static final long serialVersionUID = 2554945157018950009L;
	private OrgUnitId parentId;
	private OuType ouType;
	private OrgUnit orgUnit;

	public OrgUnitId getParentId() {
		return parentId;
	}
	public void setParentId(OrgUnitId parentId) {
		this.parentId = parentId;
	}

	public OuType getOuType() {
		return ouType;
	}
	public void setOuType(OuType ouType) {
		this.ouType = ouType;
	}
	public OrgUnit getOrgUnit() {
		return orgUnit;
	}
	public void setOrgUnit(OrgUnit orgUnit) {
		this.orgUnit = orgUnit;
	}
}
