package org.adorsys.adcost.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

/**
 * A cost/profit bearer is an identifiable element in an activity center.
 * 
 * A bearer can bear costs and/or profit. 
 * 
 * A bearer is always hosted by an activity center.
 * 
 * A bearer can be either a primary or a secondary bearer. 
 * 	- Primary bearers are only assigned costs/profits from the general accounting.
 * 	- Secondary bearers can receive costs from other bearers through the process of redistribution. 
 * 
 * 
 * @author francis
 *
 */
@Entity
@Description("CstBearer_description")
public class CstBearer extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 2129216711240922680L;

	/*
	 * The identifier of the cost center of this bearer. A bearer is always 
	 * assigned to a well defined cost center.
	 */
	@Column
	@Description("CstBearer_ctrNbr_description")
	@NotNull
	private String ctrNbr;
	
	/*
	 * The identifier of this bearer.
	 */
	@Column
	@Description("CstBearer_brrNbr_description")
	@NotNull
	private String brrNbr;

	/*
	 * The name of this bearer.
	 */
	@Column
	@Description("CstBearer_desgtn_description")
	private String desgtn;

	@Column
	@Description("CstBearer_descrp_description")
	@Min(256)
	private String descrp;

	/*
	 * Tghe type of this bearer. Whether it is a primary or a secondary bearer.
	 */
	@Column
	@Description("CstBearer_brrType_description")
	@Enumerated(EnumType.STRING)
	private CstBearerType brrType;

	/*
	 * The currency of this cost bearer.
	 */
	@Column
	@Description("CstBearer_brrCur_description")
	private String brrCur;

	@Override
	protected String makeIdentif() {
		return ctrNbr + "_" + brrNbr;
	}

	public String getCtrNbr() {
		return ctrNbr;
	}

	public void setCtrNbr(String ctrNbr) {
		this.ctrNbr = ctrNbr;
	}

	public String getBrrNbr() {
		return brrNbr;
	}

	public void setBrrNbr(String brrNbr) {
		this.brrNbr = brrNbr;
	}

	public String getDesgtn() {
		return desgtn;
	}

	public void setDesgtn(String desgtn) {
		this.desgtn = desgtn;
	}

	public String getDescrp() {
		return descrp;
	}

	public void setDescrp(String descrp) {
		this.descrp = descrp;
	}

	public CstBearerType getBrrType() {
		return brrType;
	}

	public void setBrrType(CstBearerType brrType) {
		this.brrType = brrType;
	}

	public String getBrrCur() {
		return brrCur;
	}

	public void setBrrCur(String brrCur) {
		this.brrCur = brrCur;
	}

}