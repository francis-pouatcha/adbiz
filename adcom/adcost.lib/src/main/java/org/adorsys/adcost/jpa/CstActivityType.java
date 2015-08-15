package org.adorsys.adcost.jpa;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.TmprlCstr;
import org.adorsys.adcore.enums.CoreTmprlDpndncy;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.apache.commons.lang3.StringUtils;

/**
 * Instances of an activity type are considered article lots. This class keeps
 * aditional details on the activity types. Those detail can help manage
 * quantities and valuation of those lots.
 * 
 * For example if an activity is a human service, for a period the expiration
 * time might be the period end date for the capacity available in that cost
 * center.
 * 
 * @author francis
 *
 */
@Entity
public class CstActivityType extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -3824468346973745064L;

	@Override
	protected String makeIdentif() {
		if (StringUtils.isBlank(identif))
			throw new IllegalStateException(
					"Identifier must be set before creation");
		return identif;
	}

	@Column
	@NotNull
	private String typeName;

	@Column
	@Enumerated(EnumType.STRING)
	private CoreTmprlDpndncy tmprlDpndncy;

	@Column
	@Min(256)
	private String descrp;

	/*
	 * Types de centres de coûts pour lesquels ces types d’activités sont
	 * valables pour la planification et l’imputation
	 */
	@Enumerated(EnumType.STRING)
	@TmprlCstr(CoreTmprlDpndncy.DATE)
	private Set<CstActivityCenterType> centerTypes;

	/*
	 * Catégorie de type d’activité, catégorie de type d’activité au plan
	 */
	@Enumerated(EnumType.STRING)
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private CstActvtyTypeCtgry planCategory;

	/*
	 * Catégorie de type d’activité, catégorie de type d’activité au reel
	 */
	@Enumerated(EnumType.STRING)
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private CstActvtyTypeCtgry realCategory;

	/*
	 * Indicateur de prix au plan
	 * 
	 * Détermination du calcul du prix du type d’activité dans un centre de
	 * coûts
	 */
	@Enumerated(EnumType.STRING)
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private CstPricingIndctr planPricingIndctr;

	/*
	 * Indicateur de prix au réel
	 * 
	 * Détermination du calcul du prix du type d’activité dans un centre de
	 * coûts
	 */
	@Enumerated(EnumType.STRING)
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private CstPricingIndctr realPricingIndctr;

	/*
	 * The unit of work.
	 * 
	 * Unité d’œuvre
	 * 
	 * Unité de quantité dans laquelle l’activité est imputée
	 */
	@Column
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private String unitOfWork;

	/*
	 * Measuring the unit of work.
	 * 
	 * Unité de mesure de l'unité d’œuvre
	 */
	@Column
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private String unitOfMeasure;

	/*
	 * The CstBearer. Bearer to which this activity is imputed.
	 * 
	 * Porteur d’imputation
	 * 
	 * Porteur secondaire sous laquelle le type d’activité est imputé
	 */
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private String bearer;

	/*
	 * Prix moyens
	 * 
	 * Champ permettant de déterminer si les prix des centres de coûts/types
	 * d’activités restent constants pour l’exercice tout entier
	 */
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private BigDecimal avrgPrice;

	/*
	 * Prérépartition des coûts fixes
	 * 
	 * Détermination de la répartition anticipée des coûts fixes sur les
	 * centres de coûts qui reçoivent l’activité
	 */
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private BigDecimal imputedFixCosts;

	/*
	 * Quantité planifiée saisie
	 * 
	 * Si cet indicateur est activé, la quantité d’activité planifiée n’est
	 * pas modifiée par le rapprochement du plan
	 */
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private BigDecimal planQty;

	/*
	 * quantité réelle saisie
	 * 
	 * Champ permettant de déterminer si, en dehors la quantité d’activité
	 * imputée par le centre de coûts, on peut aussi enregistrer une quantité
	 * manuelle
	 */
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private BigDecimal realQty;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date lockIndctr;

	@Column
	private String lockOwnr;

	/*
	 * Unité de production alternative
	 * 
	 * On peut définir une unité de production alternative si la production
	 * d’un type d’activité est quantifiée dans une unité différente de
	 * celle utilisée par le type d’activité lui-même.
	 */
	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private String altPrdctnUnit;

	@TmprlCstr(CoreTmprlDpndncy.ACC_YEAR)
	private BigDecimal pdctnRatioPct;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public CoreTmprlDpndncy getTmprlDpndncy() {
		return tmprlDpndncy;
	}

	public void setTmprlDpndncy(CoreTmprlDpndncy tmprlDpndncy) {
		this.tmprlDpndncy = tmprlDpndncy;
	}

	public String getDescrp() {
		return descrp;
	}

	public void setDescrp(String descrp) {
		this.descrp = descrp;
	}

	public Set<CstActivityCenterType> getCenterTypes() {
		return centerTypes;
	}

	public void setCenterTypes(Set<CstActivityCenterType> centerTypes) {
		this.centerTypes = centerTypes;
	}

	public CstActvtyTypeCtgry getPlanCategory() {
		return planCategory;
	}

	public void setPlanCategory(CstActvtyTypeCtgry planCategory) {
		this.planCategory = planCategory;
	}

	public CstActvtyTypeCtgry getRealCategory() {
		return realCategory;
	}

	public void setRealCategory(CstActvtyTypeCtgry realCategory) {
		this.realCategory = realCategory;
	}

	public CstPricingIndctr getPlanPricingIndctr() {
		return planPricingIndctr;
	}

	public void setPlanPricingIndctr(CstPricingIndctr planPricingIndctr) {
		this.planPricingIndctr = planPricingIndctr;
	}

	public CstPricingIndctr getRealPricingIndctr() {
		return realPricingIndctr;
	}

	public void setRealPricingIndctr(CstPricingIndctr realPricingIndctr) {
		this.realPricingIndctr = realPricingIndctr;
	}

	public String getUnitOfWork() {
		return unitOfWork;
	}

	public void setUnitOfWork(String unitOfWork) {
		this.unitOfWork = unitOfWork;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public String getBearer() {
		return bearer;
	}

	public void setBearer(String bearer) {
		this.bearer = bearer;
	}

	public BigDecimal getAvrgPrice() {
		return avrgPrice;
	}

	public void setAvrgPrice(BigDecimal avrgPrice) {
		this.avrgPrice = avrgPrice;
	}

	public BigDecimal getImputedFixCosts() {
		return imputedFixCosts;
	}

	public void setImputedFixCosts(BigDecimal imputedFixCosts) {
		this.imputedFixCosts = imputedFixCosts;
	}

	public BigDecimal getPlanQty() {
		return planQty;
	}

	public void setPlanQty(BigDecimal planQty) {
		this.planQty = planQty;
	}

	public BigDecimal getRealQty() {
		return realQty;
	}

	public void setRealQty(BigDecimal realQty) {
		this.realQty = realQty;
	}

	public Date getLockIndctr() {
		return lockIndctr;
	}

	public void setLockIndctr(Date lockIndctr) {
		this.lockIndctr = lockIndctr;
	}

	public String getLockOwnr() {
		return lockOwnr;
	}

	public void setLockOwnr(String lockOwnr) {
		this.lockOwnr = lockOwnr;
	}

	public String getAltPrdctnUnit() {
		return altPrdctnUnit;
	}

	public void setAltPrdctnUnit(String altPrdctnUnit) {
		this.altPrdctnUnit = altPrdctnUnit;
	}

	public BigDecimal getPdctnRatioPct() {
		return pdctnRatioPct;
	}

	public void setPdctnRatioPct(BigDecimal pdctnRatioPct) {
		this.pdctnRatioPct = pdctnRatioPct;
	}
	
	
}
