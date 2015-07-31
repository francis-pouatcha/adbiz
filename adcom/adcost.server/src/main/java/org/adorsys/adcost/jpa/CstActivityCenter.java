package org.adorsys.adcost.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstIdentifData;
import org.adorsys.javaext.description.Description;

/**
 * An activity center is a logical or physical location in the system
 * where some activities take place.
 * 
 * An activity center can be used to group similar activities, with the
 * intention of simplifying the cost and profit allocation process.
 * 
 * @author francis
 *
 */
@Entity
@Description("CstActivityCenter_description")
public class CstActivityCenter extends CoreAbstIdentifData {

	private static final long serialVersionUID = -5801244976091606410L;

	@Column
	@Description("CstActivityCenter_ctrNbr_description")
	@NotNull
	private String ctrNbr;

	@Column
	@Description("CstActivityCenter_designation_description")
	private String designation;

	@Column
	@Description("CstActivityCenter_descrp_description")
	@Min(256)
	private String descrp;

	@Column
	@Description("CstActivityCenter_reponsible_description")
	private String reponsible;

	@Column
	@Description("CstActivityCenter_orgUnit_description")
	private String orgUnit;
	
	@Column
	@Description("CstActivityCenter_ctrType_description")
	@Enumerated(EnumType.STRING)
	private CstActivityCenterType ctrType;

	@Column
	@Description("CstActivityCenter_hirarchDomain_description")
	private String hirarchDomain;

	@Column
	@Description("CstActivityCenter_ctrCur_description")
	private String ctrCur;

	@Override
	protected String makeIdentif() {
		return ctrNbr;
	}

	public String getCtrNbr() {
		return ctrNbr;
	}

	public void setCtrNbr(String ctrNbr) {
		this.ctrNbr = ctrNbr;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDescrp() {
		return descrp;
	}

	public void setDescrp(String descrp) {
		this.descrp = descrp;
	}

	public String getReponsible() {
		return reponsible;
	}

	public void setReponsible(String reponsible) {
		this.reponsible = reponsible;
	}

	public String getOrgUnit() {
		return orgUnit;
	}

	public void setOrgUnit(String orgUnit) {
		this.orgUnit = orgUnit;
	}

	public CstActivityCenterType getCtrType() {
		return ctrType;
	}

	public void setCtrType(CstActivityCenterType ctrType) {
		this.ctrType = ctrType;
	}

	public String getHirarchDomain() {
		return hirarchDomain;
	}

	public void setHirarchDomain(String hirarchDomain) {
		this.hirarchDomain = hirarchDomain;
	}

	public String getCtrCur() {
		return ctrCur;
	}

	public void setCtrCur(String ctrCur) {
		this.ctrCur = ctrCur;
	}
	
}