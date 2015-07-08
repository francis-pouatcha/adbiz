package org.adorsys.adsales.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstIdentifData;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

@Entity
@Description("SlsSOPtnr_description")
public class SlsSOPtnr extends CoreAbstIdentifData {

	private static final long serialVersionUID = -9179784936255120208L;

	@Column
	@Description("SlsSOPtnr_soNbr_description")
	@NotNull
	private String soNbr;

	@Column
	@Description("SlsSOPtnr_ptnrNbr_description")
	@NotNull
	private String ptnrNbr;

	@Column
	@Description("SlsSOPtnr_roleInSO_description")
	@NotNull
	private String roleInSO;
	
	
	// Transcient fields
	@Transient
	private String ptnrType;
	
	@Transient
	private String fullName;
	
	@Transient
	private String ctryOfRsdnc;
	

	public String getPtnrType() {
		return ptnrType;
	}

	public void setPtnrType(String ptnrType) {
		this.ptnrType = ptnrType;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCtryOfRsdnc() {
		return ctryOfRsdnc;
	}

	public void setCtryOfRsdnc(String ctryOfRsdnc) {
		this.ctryOfRsdnc = ctryOfRsdnc;
	}

	public String getSoNbr() {
		return this.soNbr;
	}

	public void setSoNbr(final String soNbr) {
		this.soNbr = soNbr;
	}

	public String getPtnrNbr() {
		return this.ptnrNbr;
	}

	public void setPtnrNbr(final String ptnrNbr) {
		this.ptnrNbr = ptnrNbr;
	}

	public String getRoleInSO() {
		return this.roleInSO;
	}

	public void setRoleInSO(final String roleInSO) {
		this.roleInSO = roleInSO;
	}
	
	public static String toId(String soNbr, String ptnrNbr, String roleInSOr){
		return soNbr + "_" + ptnrNbr + "_" + roleInSOr;
	}

	@Override
	protected String makeIdentif() {
		return soNbr + "_" + ptnrNbr + "_" + roleInSO;
	}

	public boolean contentEquals(SlsSOPtnr target) {
		if(target==null) return false;
		if(!StringUtils.equals(soNbr, target.soNbr)) return false;
		if(!StringUtils.equals(ptnrNbr, target.ptnrNbr)) return false;
		if(!StringUtils.equals(roleInSO, target.roleInSO)) return false;
		return true;
	}

	public void copyTo(SlsSOPtnr target) {
		target.ptnrNbr=ptnrNbr;
		target.soNbr=soNbr;
		target.roleInSO=roleInSO;
		
	}
}