package org.adorsys.adcore.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Holds an entity and corresponding field descriptions for a search by example
 * call.
 * 
 * @author francis pouatcha
 *
 */
@XmlRootElement
public abstract class CoreAbstBsnsObjectSearchInput<E extends CoreAbstBsnsObject> extends CoreAbstIdentifObjectSearchInput<E>{

	private Date prchWrntyDtFrom;
	
	private Date prchWrntyDtTo;

	private Date prchRtrnDtFrom;
	
	private Date prchRtrnDtTo;
	
	private Date slsWrntyDtFrom;

	private Date slsWrntyDtTo;
	
	private Date slsRtrnDtFrom;
	
	private Date slsRtrnDtTo;

	private BigDecimal prchGrossPrcPreTaxFrom;

	private BigDecimal prchGrossPrcPreTaxTo;

	private BigDecimal prchRebateAmtFrom;

	private BigDecimal prchRebateAmtTo;

	private BigDecimal prchRdngDscntAmtFrom;

	private BigDecimal prchRdngDscntAmtTo;

	private BigDecimal slsGrossPrcPreTaxFrom;

	private BigDecimal slsGrossPrcPreTaxTo;

	private BigDecimal slsRebateAmtFrom;

	private BigDecimal slsRebateAmtTo;

	private BigDecimal slsRdngDscntAmtFrom;

	private BigDecimal slsRdngDscntAmtTo;
	
	private BigDecimal stkValPreTaxFrom;

	private BigDecimal stkValPreTaxTo;

	
	public boolean noSpecialParams(){
		return prchWrntyDtFrom==null && prchWrntyDtTo==null &&
				prchRtrnDtFrom==null && prchRtrnDtTo==null && slsWrntyDtFrom==null && slsWrntyDtTo==null && slsRtrnDtFrom==null && slsRtrnDtTo==null &&
				prchGrossPrcPreTaxFrom==null && prchGrossPrcPreTaxTo==null && prchRebateAmtFrom==null && prchRebateAmtTo==null && prchRdngDscntAmtFrom==null && prchRdngDscntAmtTo==null &&
				prchRdngDscntAmtTo==null && slsGrossPrcPreTaxFrom==null && slsGrossPrcPreTaxTo==null && slsRebateAmtFrom==null && slsRebateAmtTo==null && slsRdngDscntAmtFrom==null &&
				slsRdngDscntAmtTo==null && stkValPreTaxFrom==null && stkValPreTaxTo==null;
	}

	public Date getPrchWrntyDtFrom() {
		return prchWrntyDtFrom;
	}

	public void setPrchWrntyDtFrom(Date prchWrntyDtFrom) {
		this.prchWrntyDtFrom = prchWrntyDtFrom;
	}

	public Date getPrchWrntyDtTo() {
		return prchWrntyDtTo;
	}

	public void setPrchWrntyDtTo(Date prchWrntyDtTo) {
		this.prchWrntyDtTo = prchWrntyDtTo;
	}

	public Date getPrchRtrnDtFrom() {
		return prchRtrnDtFrom;
	}

	public void setPrchRtrnDtFrom(Date prchRtrnDtFrom) {
		this.prchRtrnDtFrom = prchRtrnDtFrom;
	}

	public Date getPrchRtrnDtTo() {
		return prchRtrnDtTo;
	}

	public void setPrchRtrnDtTo(Date prchRtrnDtTo) {
		this.prchRtrnDtTo = prchRtrnDtTo;
	}

	public Date getSlsWrntyDtFrom() {
		return slsWrntyDtFrom;
	}

	public void setSlsWrntyDtFrom(Date slsWrntyDtFrom) {
		this.slsWrntyDtFrom = slsWrntyDtFrom;
	}

	public Date getSlsWrntyDtTo() {
		return slsWrntyDtTo;
	}

	public void setSlsWrntyDtTo(Date slsWrntyDtTo) {
		this.slsWrntyDtTo = slsWrntyDtTo;
	}

	public Date getSlsRtrnDtFrom() {
		return slsRtrnDtFrom;
	}

	public void setSlsRtrnDtFrom(Date slsRtrnDtFrom) {
		this.slsRtrnDtFrom = slsRtrnDtFrom;
	}

	public Date getSlsRtrnDtTo() {
		return slsRtrnDtTo;
	}

	public void setSlsRtrnDtTo(Date slsRtrnDtTo) {
		this.slsRtrnDtTo = slsRtrnDtTo;
	}

	public BigDecimal getPrchGrossPrcPreTaxFrom() {
		return prchGrossPrcPreTaxFrom;
	}

	public void setPrchGrossPrcPreTaxFrom(BigDecimal prchGrossPrcPreTaxFrom) {
		this.prchGrossPrcPreTaxFrom = prchGrossPrcPreTaxFrom;
	}

	public BigDecimal getPrchGrossPrcPreTaxTo() {
		return prchGrossPrcPreTaxTo;
	}

	public void setPrchGrossPrcPreTaxTo(BigDecimal prchGrossPrcPreTaxTo) {
		this.prchGrossPrcPreTaxTo = prchGrossPrcPreTaxTo;
	}

	public BigDecimal getPrchRebateAmtFrom() {
		return prchRebateAmtFrom;
	}

	public void setPrchRebateAmtFrom(BigDecimal prchRebateAmtFrom) {
		this.prchRebateAmtFrom = prchRebateAmtFrom;
	}

	public BigDecimal getPrchRebateAmtTo() {
		return prchRebateAmtTo;
	}

	public void setPrchRebateAmtTo(BigDecimal prchRebateAmtTo) {
		this.prchRebateAmtTo = prchRebateAmtTo;
	}

	public BigDecimal getPrchRdngDscntAmtFrom() {
		return prchRdngDscntAmtFrom;
	}

	public void setPrchRdngDscntAmtFrom(BigDecimal prchRdngDscntAmtFrom) {
		this.prchRdngDscntAmtFrom = prchRdngDscntAmtFrom;
	}

	public BigDecimal getPrchRdngDscntAmtTo() {
		return prchRdngDscntAmtTo;
	}

	public void setPrchRdngDscntAmtTo(BigDecimal prchRdngDscntAmtTo) {
		this.prchRdngDscntAmtTo = prchRdngDscntAmtTo;
	}

	public BigDecimal getSlsGrossPrcPreTaxFrom() {
		return slsGrossPrcPreTaxFrom;
	}

	public void setSlsGrossPrcPreTaxFrom(BigDecimal slsGrossPrcPreTaxFrom) {
		this.slsGrossPrcPreTaxFrom = slsGrossPrcPreTaxFrom;
	}

	public BigDecimal getSlsGrossPrcPreTaxTo() {
		return slsGrossPrcPreTaxTo;
	}

	public void setSlsGrossPrcPreTaxTo(BigDecimal slsGrossPrcPreTaxTo) {
		this.slsGrossPrcPreTaxTo = slsGrossPrcPreTaxTo;
	}

	public BigDecimal getSlsRebateAmtFrom() {
		return slsRebateAmtFrom;
	}

	public void setSlsRebateAmtFrom(BigDecimal slsRebateAmtFrom) {
		this.slsRebateAmtFrom = slsRebateAmtFrom;
	}

	public BigDecimal getSlsRebateAmtTo() {
		return slsRebateAmtTo;
	}

	public void setSlsRebateAmtTo(BigDecimal slsRebateAmtTo) {
		this.slsRebateAmtTo = slsRebateAmtTo;
	}

	public BigDecimal getSlsRdngDscntAmtFrom() {
		return slsRdngDscntAmtFrom;
	}

	public void setSlsRdngDscntAmtFrom(BigDecimal slsRdngDscntAmtFrom) {
		this.slsRdngDscntAmtFrom = slsRdngDscntAmtFrom;
	}

	public BigDecimal getSlsRdngDscntAmtTo() {
		return slsRdngDscntAmtTo;
	}

	public void setSlsRdngDscntAmtTo(BigDecimal slsRdngDscntAmtTo) {
		this.slsRdngDscntAmtTo = slsRdngDscntAmtTo;
	}

	public BigDecimal getStkValPreTaxFrom() {
		return stkValPreTaxFrom;
	}

	public void setStkValPreTaxFrom(BigDecimal stkValPreTaxFrom) {
		this.stkValPreTaxFrom = stkValPreTaxFrom;
	}

	public BigDecimal getStkValPreTaxTo() {
		return stkValPreTaxTo;
	}

	public void setStkValPreTaxTo(BigDecimal stkValPreTaxTo) {
		this.stkValPreTaxTo = stkValPreTaxTo;
	}
}
