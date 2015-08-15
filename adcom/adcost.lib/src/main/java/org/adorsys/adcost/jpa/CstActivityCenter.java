package org.adorsys.adcost.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.annotation.TmprlCstr;
import org.adorsys.adcore.enums.CoreTmprlDpndncy;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.apache.commons.lang3.StringUtils;

/**
 * An activity center is a logical or physical location in the system where some
 * activities take place.
 * 
 * An activity center can be used to group similar activities, with the
 * intention of simplifying the cost and profit allocation process.
 *
 * The centerNbr is the identifier.
 * 
 * @author francis
 *
 */
@Entity
@Description("CstActivityCenter_description")
public class CstActivityCenter extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -5801244976091606410L;

	@Column
	@Description("CstActivityCenter_designation_description")
	private String designation;

	@Column
	@Description("CstActivityCenter_descrp_description")
	@Min(256)
	private String descrp;

	@Column
	@Description("CstActivityCenter_orgUnit_description")
	@TmprlCstr(CoreTmprlDpndncy.DATE)
	private String orgUnit;

	@Column
	@Description("CstActivityCenter_reponsible_description")
	@TmprlCstr(CoreTmprlDpndncy.DATE)
	private String reponsible;

	/*
	 * The center type
	 * 
	 * Informations de planification, informations de comptabilisation
	 * (écritures primaires, secondaires, etc.)
	 */
	@Column
	@Description("CstActivityCenter_ctrType_description")
	@Enumerated(EnumType.STRING)
	@TmprlCstr(CoreTmprlDpndncy.ACC_PERIOD)
	private CstActivityCenterType ctrType;

	/*
	 * Domaine de hiérarchie
	 * 
	 * Classification des centres dans l'organigramme de l'entreprise
	 */
	@Column
	@Description("CstActivityCenter_hirarchDomain_description")
	@TmprlCstr(CoreTmprlDpndncy.ACC_PERIOD)
	private String hirarchDomain;

	/*
	 * Société
	 */
	@Column
	@Description("CstActivityCenter_orgnztn_description")
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private String orgnztn;

	/*
	 * Domaine d’activité
	 */
	@Column
	@Description("CstActivityCenter_actvtyDomain_description")
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private String actvtyDomain;

	/*
	 * Centre parent dans la hiérarchie des centres.
	 */
	@Column
	@Description("CstActivityCenter_prntOrgUnit_description")
	@TmprlCstr(CoreTmprlDpndncy.ACC_PERIOD)
	private String prntOrgUnit;

	/*
	 * Devise du centre Valeur par défaut pour la devise a utiliser.
	 */
	@Column
	@Description("CstActivityCenter_ctrCur_description")
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private String ctrCur;

	/*
	 * Moyen de communication : téléphone, télécopieur, imprimante, ligne de
	 * transmission de données, etc.
	 */
	@Column
	@Description("CstActivityCenter_comData_description")
	@TmprlCstr(CoreTmprlDpndncy.DATE)
	@Size(max = 256)
	private String comData;

	/*
	 * Adresse du centre de coûts/profit
	 */
	@Column
	@Description("CstActivityCenter_address_description")
	@TmprlCstr(CoreTmprlDpndncy.DATE)
	@Size(max = 256)
	private String address;

	/*
	 * Gestion des quantités consommées ; avertissement pour les écritures
	 * sans quantités ; aucune incidence sur la mise à jour des quantités.
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
	 * Pour les écritures réelles et planifiées (primaires, secondaires,
	 * produits, réservations)
	 */
	@Column
	@Description("CstActivityCenter_lockIndctr_description")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lockIndctr;

	/*
	 * Acteur block cette donnée.
	 */
	@Column
	@Description("CstActivityCenter_lockOwnr_description")
	private String lockOwnr;

	/*
	 * Schéma pour la planification par formule indépendante de l’activité
	 */
	@Column
	@Description("CstActivityCenter_planSchmActvtyRelated_description")
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private String planSchmActvtyRelated;

	/*
	 * Schéma pour la planification par formule dépendante de l’activité
	 */
	@Column
	@Description("CstActivityCenter_planSchmActvtyIndpdnt_description")
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private String planSchmActvtyIndpdnt;

	/*
	 * Schéma pour les imputations indépendantes de l’activité (centre de
	 * coûts)
	 */
	@Column
	@Description("CstActivityCenter_imptatnSchmActvtyRelated_description")
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private String imptatnSchmActvtyRelated;

	/*
	 * Schéma pour les imputations dépendantes de l’activité (centres de
	 * coûts/types d’activités).
	 */
	@Column
	@Description("CstActivityCenter_imptatnSchmActvtyIndpdnt_description")
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private String imptatnSchmActvtyIndpdnt;

	/*
	 * Schéma de calcul pour détermination des frais généraux
	 */
	@Column
	@Description("CstActivityCenter_cmptatnSchmOverheads_description")
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private String cmptatnSchmOverheads;

	/*
	 * Assessment and Distribution Scheme In Profit Center Accounting, the
	 * allocation function allows you to allocate the following plan and actual
	 * data. · Costs (assessment and/or distribution) · Revenues and sales
	 * deductions (assessment and/or distribution) · Balance sheet items
	 * (distribution)
	 * 
	 * Assessment is made using a special cost/revenue element.
	 * 
	 * In distribution, the original cost/revenue element/account number is
	 * retained.
	 */
	@Column
	@Description("CstActivityCenter_assmntNdDstrbtnSchm_description")
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private String assmntNdDstrbtnSchm;

	@Override
	protected String makeIdentif() {
		if (StringUtils.isBlank(identif))
			throw new IllegalStateException(
					"Identifier must be set before creation");
		return identif;
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