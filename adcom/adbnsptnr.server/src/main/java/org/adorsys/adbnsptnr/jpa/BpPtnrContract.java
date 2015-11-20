package org.adorsys.adbnsptnr.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@Entity
@Description("BpPtnrContract_description")
public class BpPtnrContract extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -1745434871112146796L;

	@Column
	@Description("BpPtnrContract_holderIdentif_description")
	@NotNull
	private String holderIdentif;

	@Column
	@Description("BpPtnrContract_contractElt_description")
	@Enumerated(EnumType.STRING)
	@NotNull
	private BpPtnrContractElt contractElt;

	@Column
	@Description("BpPtnrContract_qualif_description")
	@NotNull
	private String qualif;
	
	@Column
	@Description("BpPtnrContract_eltValue_description")
	private String eltValue;

	@Column
	@Description("BpPtnrContract_eltConstraint_description")
	private String eltConstraint;

	@Override
	protected String makeIdentif() {
		return holderIdentif +"_" +(contractElt!=null?contractElt.name():"null") + "_" + qualif;
	}

	public String getHolderIdentif() {
		return holderIdentif;
	}

	public void setHolderIdentif(String holderIdentif) {
		this.holderIdentif = holderIdentif;
	}

	public BpPtnrContractElt getContractElt() {
		return contractElt;
	}

	public void setContractElt(BpPtnrContractElt contractElt) {
		this.contractElt = contractElt;
	}

	public String getQualif() {
		return qualif;
	}

	public void setQualif(String qualif) {
		this.qualif = qualif;
	}

	public String getEltValue() {
		return eltValue;
	}

	public void setEltValue(String eltValue) {
		this.eltValue = eltValue;
	}

	public String getEltConstraint() {
		return eltConstraint;
	}

	public void setEltConstraint(String eltConstraint) {
		this.eltConstraint = eltConstraint;
	}
}