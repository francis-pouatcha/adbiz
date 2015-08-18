package org.adorsys.adcost.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.annotation.TmprlCstr;
import org.adorsys.adcore.enums.CoreTmprlDpndncy;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.apache.commons.lang3.StringUtils;

/**
 * A cost/profit bearer is an identifiable element in an activity center.
 * 
 * A bearer can bear costs and/or profit.
 * 
 * A bearer is always hosted by an activity center.
 * 
 * A bearer can be either a primary or a secondary bearer. - Primary bearers are
 * only assigned costs/profits from the general accounting. - Secondary bearers
 * can receive costs from other bearers through the process of redistribution.
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
	 * 
	 * Classification des porteurs primaires et secondaires
	 */
	@Column
	@Description("CstBearer_brrType_description")
	@Enumerated(EnumType.STRING)
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private CstBearerType brrType;

	/*
	 * The currency of this cost bearer.
	 */
	@Column
	@Description("CstBearer_brrCur_description")
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private String brrCur;

	/*
	 * Gestion des quantités consommées ;
	 * 
	 * avertissement pour les écritures sans quantités ;
	 * 
	 * aucune incidence sur la mise à jour des quantités.
	 */
	@Column
	@Description("CstActivityCenter_qtyIndctr_description")
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private String qtyIndctr;

	/*
	 * Détermination de l’unité de quantité utilisée
	 */
	@Column
	@Description("CstActivityCenter_msrmntUnit_description")
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private String msrmntUnit;

	/*
	 * Affectation au centre de coûts ou à la commande
	 */
	@Column
	@Description("CstBearer_brrCur_description")
	@TmprlCstr(CoreTmprlDpndncy.DATE)
	private String activityCtr;

	@Override
	protected String makeIdentif() {
		if (StringUtils.isBlank(identif))
			throw new IllegalStateException(
					"Identifier must be set before creation");
		return identif;
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

	public String getQtyIndctr() {
		return qtyIndctr;
	}

	public void setQtyIndctr(String qtyIndctr) {
		this.qtyIndctr = qtyIndctr;
	}

	public String getMsrmntUnit() {
		return msrmntUnit;
	}

	public void setMsrmntUnit(String msrmntUnit) {
		this.msrmntUnit = msrmntUnit;
	}

	public String getActivityCtr() {
		return activityCtr;
	}

	public void setActivityCtr(String activityCtr) {
		this.activityCtr = activityCtr;
	}
}