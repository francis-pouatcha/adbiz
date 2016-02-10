package org.adorsys.adcore.jpa;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.DateFormatPattern;
import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adcore.utils.FinancialOps;
import org.apache.commons.lang3.time.DateUtils;

@MappedSuperclass
public abstract class CoreAbstBsnsItem extends CoreAbstBsnsItemHeader {

	private static final long serialVersionUID = -3126732425840962068L;
	
	@Column
	private String lotPic;

	@Column
	@NotNull
	private String artPic;
	
	//default name
	@Column
	private String artName;

	@Column
	private String section;
	
	/*
	 * The originating code identifier. This can be the identifier of
	 * the supplier, the manufacturer, or any oder origin.
	 */
	@Column
	private String supplierPic;

	@Column
	private String supplier;

	@Column
	private String manufacturerPic;
	
	/*
	 * The expiration date of this lot. This is decisive for the splitting in lots.
	 * All articles in the same lot have the same expiration date.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirDt;

	@Column
	private BigDecimal prchUnitPrcPreTax;

	@Column
	private BigDecimal prchUnitPrcTaxIncl;

	@Column
	private BigDecimal prchRstckgFeesPrct;
	
	@Column
	private BigDecimal prchRstckgUnitFeesPreTax;
	
	@Column
	@Enumerated(EnumType.STRING)
	private CoreRstkgFeesType prchRstckgFeesType;
	
	@Column
	@NotNull
	private String prchUnitPrcCur = CoreCurrencyEnum.XAF.name();

	@Column
	private BigDecimal prchRstckgFeesPreTax;
	
	@Column
	private BigDecimal prchGrossPrcPreTax;
	
	@Column
	@Enumerated(EnumType.STRING)
	private CoreAmtOrPct prchRebateType;
	
	@Column
	private BigDecimal prchRebateAmt;

	@Column
	private BigDecimal prchRebatePct;

	@Column
	private BigDecimal prchNetPrcPreTax;

	@Column
	private BigDecimal prchVatPct;

	@Column
	private BigDecimal prchVatAmt;

	@Column
	private BigDecimal prchVatUnitAmt;
	
	@Column
	private BigDecimal prchNetPrcTaxIncl;

	@Column
	private BigDecimal prchWrntyDays;

	@Column
	private BigDecimal prchRtrnDays;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date prchWrntyDt;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date prchRtrnDt;
	
	@Column
	private BigDecimal slsUnitPrcPreTax;

	@Column
	private BigDecimal slsUnitPrcTaxIncl;
	
	@Column
	private BigDecimal slsMinUnitPrcPreTax;

	@Column
	private BigDecimal slsMinUnitPrcTaxIncl;

	@Column
	private BigDecimal slsRstckgFeesPrct;
	
	@Column
	private BigDecimal slsRstckgUnitFeesPreTax;
	
	@Column
	@Enumerated(EnumType.STRING)
	private CoreRstkgFeesType slsRstckgFeesType;
	
	@Column
	@NotNull
	private String slsUnitPrcCur = CoreCurrencyEnum.XAF.name();

	@Column
	private BigDecimal slsGrossPrcPreTax;

	@Column
	private BigDecimal slsRstckgFeesPreTax;

	@Column
	@Enumerated(EnumType.STRING)
	private CoreAmtOrPct slsRebateType;
	
	@Column
	private BigDecimal slsRebateAmt;

	@Column
	private BigDecimal slsRebatePct;

	@Column
	private BigDecimal slsNetPrcPreTax;

	@Column
	private BigDecimal slsVatPct;

	@Column
	private BigDecimal slsVatAmt;

	@Column
	private BigDecimal slsVatUnitAmt;
	
	@Column
	private BigDecimal slsNetPrcTaxIncl;

	@Column
	private BigDecimal slsWrntyDays;

	@Column
	private BigDecimal slsRtrnDays;

	@Temporal(TemporalType.TIMESTAMP)
	private Date slsWrntyDt;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date slsRtrnDt;
	
	/*
	 * The stock valuation. At any given time, this can be calculated by the system 
	 * or set manually.
	 */
	@Column
	private BigDecimal stkUnitValPreTax;
	
	@Column
	private BigDecimal stkValPreTax;

	/*
	 * Acquisition Cost per unit. This includes besides the purchase price HT the 
	 * transportation cost, and the handling cost and any purchase commission directly or indirectly assignable 
	 * to this article.
	 *  
	 * Coût de revient
	 */
	@Column
	private BigDecimal acqrdCostPuPreTax;
	@Column
	private BigDecimal acqrdCostPreTax;
	
	/*
	 * Beside the acquisition cost, this can be regularly updated with the value induces by this item being
	 * stored in the warehouse (space, guards, operation of the warehouse, ...).
	 */
	@Column
	private BigDecimal stredCostPuPreTax;
	@Column
	private BigDecimal stredCostPreTax;

	@Column
	@NotNull
	private String stkUnitValCur = CoreCurrencyEnum.XAF.name();
	
	@Column
	private String orgUnit;

	@Column
	private String acsngUser;
	
	@Column
	private BigDecimal trgtQty;	

	@Temporal(TemporalType.TIMESTAMP)
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date disabledDt;

	/*
	 * Set the date from with a conflict occured in this inventory.
	 * A conflict occurs when tow or more inventories of the same 
	 * item have diferrents outcomes. Or when there is no inventory
	 * at all.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date conflictDt;
	
	@Column
	@NotNull
	private String salIndex;

	@Column
	@NotNull
	private String usalIndex;
	
	@Column
	private Boolean editing;

	@Temporal(TemporalType.TIMESTAMP)
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date acsngDt;
	
	@Column
	@Description("StkMvnt_mvntType_description")
	@Enumerated(EnumType.STRING)
	private CoreStkMvtType mvntType;

	@Column
	@Description("StkMvnt_mvntOrigin_description")
	@Enumerated(EnumType.STRING)
	private CoreStkMvtTerminal mvntOrigin;

	@Column
	private String mvntOriginIdentif;
	
	@Column
	@Description("StkMvnt_mvntDest_description")
	@Enumerated(EnumType.STRING)
	private CoreStkMvtTerminal mvntDest;

	@Column
	private String mvntDestIdentif;

	// This is just a reservation
	private Boolean rsvd = Boolean.FALSE;
	
	// This is just a proforma
	private Boolean profmt = Boolean.FALSE;
	
	@PrePersist
	public void prePersist() {
		super.prePersist();
		salIndex = toSalIndex(getSection(), getArtPic(), getLotPic());
		usalIndex = toUsalIndex(acsngUser, getSection(), getArtPic(), getLotPic());
		evlte();
	}
	
	@PreUpdate
	public void preUpdate() {
		usalIndex = toUsalIndex(acsngUser, getSection(), getArtPic(), getLotPic());
		evlte();
	}

	public static String toSalIndex(String section, String artPic, String lotPic){
		return section + "_" + artPic + "_" + lotPic;
	}
	public static String toUsalIndex(String acsngUser, String section, String artPic, String lotPic){
		return acsngUser + "_" +toSalIndex(section, artPic, lotPic);
	}
	
	public CoreStkMvtType getMvntType() {
		return mvntType;
	}

	public CoreStkMvtTerminal getMvntOrigin() {
		return mvntOrigin;
	}

	public void setMvntOrigin(CoreStkMvtTerminal mvntOrigin) {
		this.mvntOrigin = mvntOrigin;
	}

	public String getMvntOriginIdentif() {
		return mvntOriginIdentif;
	}

	public void setMvntOriginIdentif(String mvntOriginIdentif) {
		this.mvntOriginIdentif = mvntOriginIdentif;
	}

	public CoreStkMvtTerminal getMvntDest() {
		return mvntDest;
	}

	public void setMvntDest(CoreStkMvtTerminal mvntDest) {
		this.mvntDest = mvntDest;
	}

	public String getMvntDestIdentif() {
		return mvntDestIdentif;
	}

	public void setMvntDestIdentif(String mvntDestIdentif) {
		this.mvntDestIdentif = mvntDestIdentif;
	}

	public Boolean getRsvd() {
		return rsvd;
	}

	public void setRsvd(Boolean rsvd) {
		this.rsvd = rsvd;
	}

	public Boolean getProfmt() {
		return profmt;
	}

	public void setProfmt(Boolean profmt) {
		this.profmt = profmt;
	}

	public void setMvntType(CoreStkMvtType mvntType) {
		this.mvntType = mvntType;
	}

	@Override
	protected String makeIdentif() {
		return UUID.randomUUID().toString();
	}
	
	public Boolean getEditing() {
		return editing;
	}

	public void setEditing(final Boolean editing) {
		this.editing = editing;
	}

	public Date getAcsngDt() {
		return this.acsngDt;
	}

	public void setAcsngDt(final Date acsngDt) {
		this.acsngDt = acsngDt;
	}
	
	public String getAcsngUser() {
		return acsngUser;
	}

	public void setAcsngUser(final String acsngUser) {
		this.acsngUser = acsngUser;
	}

	public Date getDisabledDt() {
		return disabledDt;
	}

	public void setDisabledDt(final Date disabledDt) {
		this.disabledDt = disabledDt;
	}

	public Date getConflictDt() {
		return conflictDt;
	}

	public void setConflictDt(final Date conflictDt) {
		this.conflictDt = conflictDt;
	}

	public String getSalIndex() {
		return salIndex;
	}

	public void setSalIndex(String salIndex) {
		this.salIndex = salIndex;
	}

	public String getUsalIndex() {
		return usalIndex;
	}

	public void setUsalIndex(String usalIndex) {
		this.usalIndex = usalIndex;
	}

	public String getLotPic() {
		return this.lotPic;
	}

	public void setLotPic(final String lotPic) {
		this.lotPic = lotPic;
	}

	public String getArtPic() {
		return this.artPic;
	}

	public void setArtPic(final String artPic) {
		this.artPic = artPic;
	}

	public Date getExpirDt() {
		return this.expirDt;
	}

	public void setExpirDt(final Date expirDt) {
		this.expirDt = expirDt;
	}

	public String getSupplierPic() {
		return supplierPic;
	}

	public void setSupplierPic(String supplierPic) {
		this.supplierPic = supplierPic;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getManufacturerPic() {
		return manufacturerPic;
	}

	public void setManufacturerPic(String manufacturerPic) {
		this.manufacturerPic = manufacturerPic;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getOrgUnit() {
		return orgUnit;
	}

	public void setOrgUnit(String orgUnit) {
		this.orgUnit = orgUnit;
	}

	public BigDecimal getPrchUnitPrcPreTax() {
		return prchUnitPrcPreTax;
	}

	public void setPrchUnitPrcPreTax(BigDecimal prchUnitPrcPreTax) {
		this.prchUnitPrcPreTax = prchUnitPrcPreTax;
	}

	public String getPrchUnitPrcCur() {
		return prchUnitPrcCur;
	}

	public void setPrchUnitPrcCur(String prchUnitPrcCur) {
		this.prchUnitPrcCur = prchUnitPrcCur;
	}

	public BigDecimal getPrchGrossPrcPreTax() {
		return prchGrossPrcPreTax;
	}

	public void setPrchGrossPrcPreTax(BigDecimal prchGrossPrcPreTax) {
		this.prchGrossPrcPreTax = prchGrossPrcPreTax;
	}

	public CoreAmtOrPct getPrchRebateType() {
		return prchRebateType;
	}

	public void setPrchRebateType(CoreAmtOrPct prchRebateType) {
		this.prchRebateType = prchRebateType;
	}

	public BigDecimal getPrchRebateAmt() {
		return prchRebateAmt;
	}

	public void setPrchRebateAmt(BigDecimal prchRebateAmt) {
		this.prchRebateAmt = prchRebateAmt;
	}

	public BigDecimal getPrchRebatePct() {
		return prchRebatePct;
	}

	public void setPrchRebatePct(BigDecimal prchRebatePct) {
		this.prchRebatePct = prchRebatePct;
	}

	public BigDecimal getPrchNetPrcPreTax() {
		return prchNetPrcPreTax;
	}

	public void setPrchNetPrcPreTax(BigDecimal prchNetPrcPreTax) {
		this.prchNetPrcPreTax = prchNetPrcPreTax;
	}

	public BigDecimal getPrchVatPct() {
		return prchVatPct;
	}

	public void setPrchVatPct(BigDecimal prchVatPct) {
		this.prchVatPct = prchVatPct;
	}

	public BigDecimal getPrchVatAmt() {
		return prchVatAmt;
	}

	public void setPrchVatAmt(BigDecimal prchVatAmt) {
		this.prchVatAmt = prchVatAmt;
	}

	public BigDecimal getPrchNetPrcTaxIncl() {
		return prchNetPrcTaxIncl;
	}

	public void setPrchNetPrcTaxIncl(BigDecimal prchNetPrcTaxIncl) {
		this.prchNetPrcTaxIncl = prchNetPrcTaxIncl;
	}

	public BigDecimal getPrchWrntyDays() {
		return prchWrntyDays;
	}

	public void setPrchWrntyDays(BigDecimal prchWrntyDays) {
		this.prchWrntyDays = prchWrntyDays;
	}

	public BigDecimal getPrchRtrnDays() {
		return prchRtrnDays;
	}

	public void setPrchRtrnDays(BigDecimal prchRtrnDays) {
		this.prchRtrnDays = prchRtrnDays;
	}

	public BigDecimal getSlsUnitPrcPreTax() {
		return slsUnitPrcPreTax;
	}

	public void setSlsUnitPrcPreTax(BigDecimal slsUnitPrcPreTax) {
		this.slsUnitPrcPreTax = slsUnitPrcPreTax;
	}

	public BigDecimal getSlsMinUnitPrcPreTax() {
		return slsMinUnitPrcPreTax;
	}

	public void setSlsMinUnitPrcPreTax(BigDecimal slsMinUnitPrcPreTax) {
		this.slsMinUnitPrcPreTax = slsMinUnitPrcPreTax;
	}

	public String getSlsUnitPrcCur() {
		return slsUnitPrcCur;
	}

	public void setSlsUnitPrcCur(String slsUnitPrcCur) {
		this.slsUnitPrcCur = slsUnitPrcCur;
	}

	public BigDecimal getSlsGrossPrcPreTax() {
		return slsGrossPrcPreTax;
	}

	public void setSlsGrossPrcPreTax(BigDecimal slsGrossPrcPreTax) {
		this.slsGrossPrcPreTax = slsGrossPrcPreTax;
	}

	public CoreAmtOrPct getSlsRebateType() {
		return slsRebateType;
	}

	public void setSlsRebateType(CoreAmtOrPct slsRebateType) {
		this.slsRebateType = slsRebateType;
	}

	public BigDecimal getSlsRebateAmt() {
		return slsRebateAmt;
	}

	public void setSlsRebateAmt(BigDecimal slsRebateAmt) {
		this.slsRebateAmt = slsRebateAmt;
	}

	public BigDecimal getSlsRebatePct() {
		return slsRebatePct;
	}

	public void setSlsRebatePct(BigDecimal slsRebatePct) {
		this.slsRebatePct = slsRebatePct;
	}

	public BigDecimal getSlsNetPrcPreTax() {
		return slsNetPrcPreTax;
	}

	public void setSlsNetPrcPreTax(BigDecimal slsNetPrcPreTax) {
		this.slsNetPrcPreTax = slsNetPrcPreTax;
	}

	public BigDecimal getSlsVatPct() {
		return slsVatPct;
	}

	public void setSlsVatPct(BigDecimal slsVatPct) {
		this.slsVatPct = slsVatPct;
	}

	public BigDecimal getSlsVatAmt() {
		return slsVatAmt;
	}

	public void setSlsVatAmt(BigDecimal slsVatAmt) {
		this.slsVatAmt = slsVatAmt;
	}

	public BigDecimal getSlsNetPrcTaxIncl() {
		return slsNetPrcTaxIncl;
	}

	public void setSlsNetPrcTaxIncl(BigDecimal slsNetPrcTaxIncl) {
		this.slsNetPrcTaxIncl = slsNetPrcTaxIncl;
	}

	public BigDecimal getSlsWrntyDays() {
		return slsWrntyDays;
	}

	public void setSlsWrntyDays(BigDecimal slsWrntyDays) {
		this.slsWrntyDays = slsWrntyDays;
	}

	public BigDecimal getSlsRtrnDays() {
		return slsRtrnDays;
	}

	public void setSlsRtrnDays(BigDecimal slsRtrnDays) {
		this.slsRtrnDays = slsRtrnDays;
	}

	public BigDecimal getPrchUnitPrcTaxIncl() {
		return prchUnitPrcTaxIncl;
	}

	public void setPrchUnitPrcTaxIncl(BigDecimal prchUnitPrcTaxIncl) {
		this.prchUnitPrcTaxIncl = prchUnitPrcTaxIncl;
	}

	public BigDecimal getPrchVatUnitAmt() {
		return prchVatUnitAmt;
	}

	public void setPrchVatUnitAmt(BigDecimal prchVatUnitAmt) {
		this.prchVatUnitAmt = prchVatUnitAmt;
	}

	public Date getPrchWrntyDt() {
		return prchWrntyDt;
	}

	public void setPrchWrntyDt(Date prchWrntyDt) {
		this.prchWrntyDt = prchWrntyDt;
	}

	public Date getPrchRtrnDt() {
		return prchRtrnDt;
	}
	
	public void setArtName(String artName) {
		this.artName = artName;
	}
	
	public String getArtName() {
		return this.artName;
	}

	public void setPrchRtrnDt(Date prchRtrnDt) {
		this.prchRtrnDt = prchRtrnDt;
	}

	public BigDecimal getSlsUnitPrcTaxIncl() {
		return slsUnitPrcTaxIncl;
	}

	public void setSlsUnitPrcTaxIncl(BigDecimal slsUnitPrcTaxIncl) {
		this.slsUnitPrcTaxIncl = slsUnitPrcTaxIncl;
	}

	public BigDecimal getSlsVatUnitAmt() {
		return slsVatUnitAmt;
	}

	public void setSlsVatUnitAmt(BigDecimal slsVatUnitAmt) {
		this.slsVatUnitAmt = slsVatUnitAmt;
	}

	public Date getSlsWrntyDt() {
		return slsWrntyDt;
	}

	public void setSlsWrntyDt(Date slsWrntyDt) {
		this.slsWrntyDt = slsWrntyDt;
	}

	public Date getSlsRtrnDt() {
		return slsRtrnDt;
	}

	public void setSlsRtrnDt(Date slsRtrnDt) {
		this.slsRtrnDt = slsRtrnDt;
	}

	public BigDecimal getPrchRstckgFeesPrct() {
		return prchRstckgFeesPrct;
	}

	public void setPrchRstckgFeesPrct(BigDecimal prchRstckgFeesPrct) {
		this.prchRstckgFeesPrct = prchRstckgFeesPrct;
	}

	public BigDecimal getPrchRstckgUnitFeesPreTax() {
		return prchRstckgUnitFeesPreTax;
	}

	public void setPrchRstckgUnitFeesPreTax(BigDecimal prchRstckgUnitFeesPreTax) {
		this.prchRstckgUnitFeesPreTax = prchRstckgUnitFeesPreTax;
	}

	public CoreRstkgFeesType getPrchRstckgFeesType() {
		return prchRstckgFeesType;
	}

	public void setPrchRstckgFeesType(CoreRstkgFeesType prchRstckgFeesType) {
		this.prchRstckgFeesType = prchRstckgFeesType;
	}

	public BigDecimal getSlsMinUnitPrcTaxIncl() {
		return slsMinUnitPrcTaxIncl;
	}

	public void setSlsMinUnitPrcTaxIncl(BigDecimal slsMinUnitPrcTaxIncl) {
		this.slsMinUnitPrcTaxIncl = slsMinUnitPrcTaxIncl;
	}

	public BigDecimal getSlsRstckgFeesPrct() {
		return slsRstckgFeesPrct;
	}

	public void setSlsRstckgFeesPrct(BigDecimal slsRstckgFeesPrct) {
		this.slsRstckgFeesPrct = slsRstckgFeesPrct;
	}

	public BigDecimal getSlsRstckgUnitFeesPreTax() {
		return slsRstckgUnitFeesPreTax;
	}

	public void setSlsRstckgUnitFeesPreTax(BigDecimal slsRstckgUnitFeesPreTax) {
		this.slsRstckgUnitFeesPreTax = slsRstckgUnitFeesPreTax;
	}

	public CoreRstkgFeesType getSlsRstckgFeesType() {
		return slsRstckgFeesType;
	}

	public void setSlsRstckgFeesType(CoreRstkgFeesType slsRstckgFeesType) {
		this.slsRstckgFeesType = slsRstckgFeesType;
	}

	public void copyTo(CoreAbstBsnsItem target){
		super.copyTo(target);
		target.resetHeader();
	}

	public BigDecimal getStkUnitValPreTax() {
		return stkUnitValPreTax;
	}

	public void setStkUnitValPreTax(BigDecimal stkUnitValPreTax) {
		this.stkUnitValPreTax = stkUnitValPreTax;
	}

	public BigDecimal getStkValPreTax() {
		return stkValPreTax;
	}

	public void setStkValPreTax(BigDecimal stkValPreTax) {
		this.stkValPreTax = stkValPreTax;
	}

	public String getStkUnitValCur() {
		return stkUnitValCur;
	}

	public void setStkUnitValCur(String stkUnitValCur) {
		this.stkUnitValCur = stkUnitValCur;
	}

	public BigDecimal getPrchRstckgFeesPreTax() {
		return prchRstckgFeesPreTax;
	}

	public void setPrchRstckgFeesPreTax(BigDecimal prchRstckgFeesPreTax) {
		this.prchRstckgFeesPreTax = prchRstckgFeesPreTax;
	}

	public BigDecimal getSlsRstckgFeesPreTax() {
		return slsRstckgFeesPreTax;
	}

	public void setSlsRstckgFeesPreTax(BigDecimal slsRstckgFeesPreTax) {
		this.slsRstckgFeesPreTax = slsRstckgFeesPreTax;
	}

	public void addPrchRebateAmt(BigDecimal prchRebateAmt) {
		this.prchRebateAmt = BigDecimalUtils.sum(this.prchRebateAmt, prchRebateAmt);
	}
	public void addAccsdQty(BigDecimal trgtQty) {
		this.trgtQty = BigDecimalUtils.sum(this.trgtQty, trgtQty);
	}

	public void evltPrchWrntyDt(){
		if(getValueDt()!=null && prchWrntyDays!=null)
			prchWrntyDt = DateUtils.addDays(getValueDt(), prchWrntyDays.intValue());
	}
	
	public void evltPrchRtrnDt(){
		if(getValueDt()!=null && prchRtrnDays!=null)
			prchRtrnDt = DateUtils.addDays(getValueDt(), prchRtrnDays.intValue());
	}

	public void evltSlsWrntyDt(){
		if(getValueDt()!=null && slsWrntyDays!=null)
			slsWrntyDt = DateUtils.addDays(getValueDt(), slsWrntyDays.intValue());
	}
	
	public void evltSlsRtrnDt(){
		if(getValueDt()!=null && slsRtrnDays!=null)
			slsRtrnDt = DateUtils.addDays(getValueDt(), slsRtrnDays.intValue());
	}

	public BigDecimal getTrgtQty() {
		return trgtQty;
	}

	public void setTrgtQty(BigDecimal trgtQty) {
		this.trgtQty = trgtQty;
	}

	public BigDecimal getAcqrdCostPuPreTax() {
		return acqrdCostPuPreTax;
	}

	public void setAcqrdCostPuPreTax(BigDecimal acqrdCostPuPreTax) {
		this.acqrdCostPuPreTax = acqrdCostPuPreTax;
	}

	public BigDecimal getAcqrdCostPreTax() {
		return acqrdCostPreTax;
	}

	public void setAcqrdCostPreTax(BigDecimal acqrdCostPreTax) {
		this.acqrdCostPreTax = acqrdCostPreTax;
	}

	public BigDecimal getStredCostPuPreTax() {
		return stredCostPuPreTax;
	}

	public void setStredCostPuPreTax(BigDecimal stredCostPuPreTax) {
		this.stredCostPuPreTax = stredCostPuPreTax;
	}

	public BigDecimal getStredCostPreTax() {
		return stredCostPreTax;
	}

	public void setStredCostPreTax(BigDecimal stredCostPreTax) {
		this.stredCostPreTax = stredCostPreTax;
	}

	protected void normalize(){
		this.trgtQty = BigDecimalUtils.zeroIfNull(this.trgtQty);
		this.prchVatPct=BigDecimalUtils.zeroIfNull(this.prchVatPct);
		this.prchVatAmt=BigDecimalUtils.zeroIfNull(this.prchVatAmt);
		this.prchUnitPrcPreTax = BigDecimalUtils.zeroIfNull(this.prchUnitPrcPreTax);
		this.prchRebatePct=BigDecimalUtils.zeroIfNull(this.prchRebatePct);
		this.prchRebateAmt = BigDecimalUtils.zeroIfNull(this.prchRebateAmt);
		this.prchVatUnitAmt = FinancialOps.amtFromPrct(this.prchUnitPrcPreTax, this.prchVatPct, this.prchUnitPrcCur);
		this.prchUnitPrcTaxIncl = FinancialOps.add(this.prchUnitPrcPreTax, this.prchVatUnitAmt, this.prchUnitPrcCur);
		if(this.prchRstckgFeesType==null){
			if(!BigDecimalUtils.isNullOrZero(this.prchRstckgFeesPrct)){
				this.prchRstckgFeesType=CoreRstkgFeesType.PERCENT_OF_UNIT;
			} else if (!BigDecimalUtils.isNullOrZero(this.prchRstckgUnitFeesPreTax)){
				this.prchRstckgFeesType=CoreRstkgFeesType.AMOUNT_PER_UNIT;
			} else {
				this.prchRstckgFeesType=CoreRstkgFeesType.PERCENT_OF_UNIT;
			}
		}
		this.prchRstckgFeesPrct=BigDecimalUtils.zeroIfNull(this.prchRstckgFeesPrct);
		this.prchRstckgUnitFeesPreTax=BigDecimalUtils.zeroIfNull(this.prchRstckgUnitFeesPreTax);
		if(CoreRstkgFeesType.PERCENT_OF_UNIT.equals(this.prchRstckgFeesType)){
			this.prchRstckgUnitFeesPreTax = FinancialOps.amtFromPrct(this.prchUnitPrcPreTax, this.prchRstckgFeesPrct, this.prchUnitPrcCur);
		} else {
			this.prchRstckgFeesPrct = FinancialOps.prctFromAmt(this.prchUnitPrcPreTax, this.prchRstckgUnitFeesPreTax, this.prchUnitPrcCur);
		}

		this.slsVatPct=BigDecimalUtils.zeroIfNull(this.slsVatPct);
		this.prchVatAmt=BigDecimalUtils.zeroIfNull(this.prchVatAmt);
		this.slsUnitPrcPreTax = BigDecimalUtils.zeroIfNull(this.slsUnitPrcPreTax);
		this.slsRebatePct=BigDecimalUtils.zeroIfNull(this.slsRebatePct);
		this.slsRebateAmt = BigDecimalUtils.zeroIfNull(this.slsRebateAmt);
		this.slsVatUnitAmt = FinancialOps.amtFromPrct(this.slsUnitPrcPreTax, this.slsVatPct, this.slsUnitPrcCur);
		this.slsUnitPrcTaxIncl = FinancialOps.add(this.slsUnitPrcPreTax, this.slsVatUnitAmt, this.slsUnitPrcCur);
		
		if(this.slsRstckgFeesType==null){
			if(!BigDecimalUtils.isNullOrZero(this.slsRstckgFeesPrct)){
				this.slsRstckgFeesType=CoreRstkgFeesType.PERCENT_OF_UNIT;
			} else if (!BigDecimalUtils.isNullOrZero(this.slsRstckgUnitFeesPreTax)){
				this.slsRstckgFeesType=CoreRstkgFeesType.AMOUNT_PER_UNIT;
			} else {
				this.slsRstckgFeesType=CoreRstkgFeesType.PERCENT_OF_UNIT;
			}
		}
		this.slsRstckgFeesPrct=BigDecimalUtils.zeroIfNull(this.slsRstckgFeesPrct);
		this.slsRstckgUnitFeesPreTax=BigDecimalUtils.zeroIfNull(this.slsRstckgUnitFeesPreTax);
		if(CoreRstkgFeesType.PERCENT_OF_UNIT.equals(this.slsRstckgFeesType)){
			this.slsRstckgUnitFeesPreTax = FinancialOps.amtFromPrct(this.slsUnitPrcPreTax, this.slsRstckgFeesPrct, this.slsUnitPrcCur);
		} else {
			this.slsRstckgFeesPrct = FinancialOps.prctFromAmt(this.slsUnitPrcPreTax, this.slsRstckgUnitFeesPreTax, this.slsUnitPrcCur);
		}

		this.stkUnitValPreTax = BigDecimalUtils.zeroIfNull(this.stkUnitValPreTax);
		this.stkValPreTax = BigDecimalUtils.zeroIfNull(this.stkValPreTax);

		this.acqrdCostPuPreTax = BigDecimalUtils.zeroIfNull(this.acqrdCostPuPreTax);
		this.acqrdCostPreTax = BigDecimalUtils.zeroIfNull(this.acqrdCostPreTax);
		
		this.stredCostPuPreTax = BigDecimalUtils.zeroIfNull(this.stredCostPuPreTax);
		this.stredCostPreTax = BigDecimalUtils.zeroIfNull(this.stredCostPreTax);
	}
	
	protected void computeSlsNetPrcTaxIncl() {
		if(CoreAmtOrPct.PERCENT.equals(getSlsRebateType())){
			this.slsRebatePct = FinancialOps.amtFromPrct(this.slsGrossPrcPreTax, this.slsRebatePct, this.slsUnitPrcCur);
		} else {
			this.slsRebatePct = FinancialOps.prctFromAmt(this.slsGrossPrcPreTax, this.slsRebateAmt, this.slsUnitPrcCur);
		}

		this.slsNetPrcPreTax = FinancialOps.substract(this.slsGrossPrcPreTax, this.slsRebateAmt, this.slsUnitPrcCur);

		this.slsVatAmt=BigDecimalUtils.zeroIfNull(this.slsVatAmt);

		this.slsVatAmt = FinancialOps.amtFromPrct(this.slsNetPrcPreTax, this.slsVatPct, this.slsUnitPrcCur);

		this.slsNetPrcTaxIncl = FinancialOps.add(this.slsNetPrcPreTax, this.slsVatAmt, this.slsUnitPrcCur);

	}
	
	protected void computePrchNetPrcTaxIncl() {

		if(CoreAmtOrPct.PERCENT.equals(this.prchRebateType)){
			this.prchRebateAmt = FinancialOps.amtFromPrct(this.prchGrossPrcPreTax, this.prchRebatePct, this.prchUnitPrcCur);
		} else {
			this.prchRebatePct = FinancialOps.prctFromAmt(this.prchGrossPrcPreTax, this.prchRebateAmt, this.prchUnitPrcCur);
		}

		this.prchNetPrcPreTax = FinancialOps.substract(this.prchGrossPrcPreTax, this.prchRebateAmt, this.prchUnitPrcCur);

		this.prchVatAmt=BigDecimalUtils.zeroIfNull(this.prchVatAmt);
		

		this.prchVatAmt = FinancialOps.amtFromPrct(this.prchNetPrcPreTax, this.prchVatPct, this.prchUnitPrcCur);

		this.prchNetPrcTaxIncl = FinancialOps.add(this.prchNetPrcPreTax, this.prchVatAmt, this.prchUnitPrcCur);
	}

	protected void computeValAmnt() {

		setStkValPreTax(FinancialOps.qtyTmsPrice(getTrgtQty(), getStkUnitValPreTax(), getStkUnitValCur()));

		setAcqrdCostPreTax(FinancialOps.qtyTmsPrice(getTrgtQty(), getAcqrdCostPuPreTax(), getStkUnitValCur()));
		
		setStredCostPreTax(FinancialOps.qtyTmsPrice(getTrgtQty(), getStredCostPuPreTax(), getStkUnitValCur()));
	}
	
	public void evlte() {
		normalize();
		setSlsGrossPrcPreTax(FinancialOps.qtyTmsPrice(this.trgtQty, getSlsUnitPrcPreTax(), getSlsUnitPrcCur()));
		computeSlsNetPrcTaxIncl();
		setPrchGrossPrcPreTax(FinancialOps.qtyTmsPrice(this.trgtQty, getPrchUnitPrcPreTax(), getPrchUnitPrcCur()));
		computeSlsNetPrcTaxIncl();
		setStkValPreTax(FinancialOps.qtyTmsPrice(this.trgtQty, getStkUnitValPreTax(), getStkUnitValCur()));
		computeValAmnt();
	}
	
}