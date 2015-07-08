package org.adorsys.adsales.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstIdentifData;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

@Entity
@Description("SlsInvcePtnr_description")
public class SlsInvcePtnr extends CoreAbstIdentifData {

	private static final long serialVersionUID = -6896759485795494124L;

	@Column
	@Description("SlsInvcePtnr_invceNbr_description")
	@NotNull
	private String invceNbr;

	@Column
	@Description("SlsInvcePtnr_ptnrNbr_description")
	@NotNull
	private String ptnrNbr;

	@Column
	@Description("SlsInvcePtnr_roleInInvce_description")
	@NotNull
	private String roleInInvce;

	public String getInvceNbr() {
		return this.invceNbr;
	}

	public void setInvceNbr(final String invceNbr) {
		this.invceNbr = invceNbr;
	}

	public String getPtnrNbr() {
		return this.ptnrNbr;
	}

	public void setPtnrNbr(final String ptnrNbr) {
		this.ptnrNbr = ptnrNbr;
	}

	public String getRoleInInvce() {
		return this.roleInInvce;
	}

	public void setRoleInInvce(final String roleInInvce) {
		this.roleInInvce = roleInInvce;
	}
	
	public static String toId(String invceNbr, String ptnrNbr, String roleInInvce){
		return invceNbr + "_" + ptnrNbr + "_" + roleInInvce;
	}

	@Override
	protected String makeIdentif() {
		return invceNbr + "_" + ptnrNbr + "_" + roleInInvce;
	}
	
	public boolean contentEquals(SlsInvcePtnr target) {
		if(target==null) return false;
		if(!StringUtils.equals(invceNbr, target.invceNbr)) return false;
		if(!StringUtils.equals(ptnrNbr, target.ptnrNbr)) return false;
		if(!StringUtils.equals(roleInInvce, target.roleInInvce)) return false;
		return true;
	}

	public void copyTo(SlsInvcePtnr target) {
		target.ptnrNbr=ptnrNbr;
		target.invceNbr=invceNbr;
		target.roleInInvce=roleInInvce;
		
	}
}