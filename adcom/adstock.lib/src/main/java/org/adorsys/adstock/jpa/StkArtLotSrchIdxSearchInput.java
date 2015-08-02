package org.adorsys.adstock.jpa;

import java.math.BigDecimal;
import java.util.Date;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;

/**
 * Holds an entity and corresponding field descriptions 
 * for a search by example call.
 * 
 * @author francis pouatcha
 *
 */
public class StkArtLotSrchIdxSearchInput extends CoreAbstIdentifObjectSearchInput<StkArtLotSrchIdx>{
	

	private Date expirDtFrom;

	private Date expirDtTo;
	
	
	private BigDecimal slsUnitPrcPreTaxFrom;
	private BigDecimal slsUnitPrcPreTaxTo;

	private BigDecimal slsUnitPrcTaxInclFrom;
	private BigDecimal slsUnitPrcTaxInclTo;
	
	private BigDecimal slsMinUnitPrcPreTaxFrom;
	private BigDecimal slsMinUnitPrcPreTaxTo;

	private BigDecimal slsMinUnitPrcTaxInclFrom;
	private BigDecimal slsMinUnitPrcTaxInclTo;

	private BigDecimal slsVatPctFrom;
	private BigDecimal slsVatPctTo;
	
	private String rangeStart;
	private String rangeEnd;
	
	public Date getExpirDtFrom() {
		return expirDtFrom;
	}
	public void setExpirDtFrom(Date expirDtFrom) {
		this.expirDtFrom = expirDtFrom;
	}
	public Date getExpirDtTo() {
		return expirDtTo;
	}
	public void setExpirDtTo(Date expirDtTo) {
		this.expirDtTo = expirDtTo;
	}
	public BigDecimal getSlsUnitPrcPreTaxFrom() {
		return slsUnitPrcPreTaxFrom;
	}
	public void setSlsUnitPrcPreTaxFrom(BigDecimal slsUnitPrcPreTaxFrom) {
		this.slsUnitPrcPreTaxFrom = slsUnitPrcPreTaxFrom;
	}
	public BigDecimal getSlsUnitPrcPreTaxTo() {
		return slsUnitPrcPreTaxTo;
	}
	public void setSlsUnitPrcPreTaxTo(BigDecimal slsUnitPrcPreTaxTo) {
		this.slsUnitPrcPreTaxTo = slsUnitPrcPreTaxTo;
	}
	public BigDecimal getSlsUnitPrcTaxInclFrom() {
		return slsUnitPrcTaxInclFrom;
	}
	public void setSlsUnitPrcTaxInclFrom(BigDecimal slsUnitPrcTaxInclFrom) {
		this.slsUnitPrcTaxInclFrom = slsUnitPrcTaxInclFrom;
	}
	public BigDecimal getSlsUnitPrcTaxInclTo() {
		return slsUnitPrcTaxInclTo;
	}
	public void setSlsUnitPrcTaxInclTo(BigDecimal slsUnitPrcTaxInclTo) {
		this.slsUnitPrcTaxInclTo = slsUnitPrcTaxInclTo;
	}
	public BigDecimal getSlsMinUnitPrcPreTaxFrom() {
		return slsMinUnitPrcPreTaxFrom;
	}
	public void setSlsMinUnitPrcPreTaxFrom(BigDecimal slsMinUnitPrcPreTaxFrom) {
		this.slsMinUnitPrcPreTaxFrom = slsMinUnitPrcPreTaxFrom;
	}
	public BigDecimal getSlsMinUnitPrcPreTaxTo() {
		return slsMinUnitPrcPreTaxTo;
	}
	public void setSlsMinUnitPrcPreTaxTo(BigDecimal slsMinUnitPrcPreTaxTo) {
		this.slsMinUnitPrcPreTaxTo = slsMinUnitPrcPreTaxTo;
	}
	public BigDecimal getSlsMinUnitPrcTaxInclFrom() {
		return slsMinUnitPrcTaxInclFrom;
	}
	public void setSlsMinUnitPrcTaxInclFrom(BigDecimal slsMinUnitPrcTaxInclFrom) {
		this.slsMinUnitPrcTaxInclFrom = slsMinUnitPrcTaxInclFrom;
	}
	public BigDecimal getSlsMinUnitPrcTaxInclTo() {
		return slsMinUnitPrcTaxInclTo;
	}
	public void setSlsMinUnitPrcTaxInclTo(BigDecimal slsMinUnitPrcTaxInclTo) {
		this.slsMinUnitPrcTaxInclTo = slsMinUnitPrcTaxInclTo;
	}
	public BigDecimal getSlsVatPctFrom() {
		return slsVatPctFrom;
	}
	public void setSlsVatPctFrom(BigDecimal slsVatPctFrom) {
		this.slsVatPctFrom = slsVatPctFrom;
	}
	public BigDecimal getSlsVatPctTo() {
		return slsVatPctTo;
	}
	public void setSlsVatPctTo(BigDecimal slsVatPctTo) {
		this.slsVatPctTo = slsVatPctTo;
	}
	public String getRangeStart() {
		return rangeStart;
	}
	public void setRangeStart(String rangeStart) {
		this.rangeStart = rangeStart;
	}
	public String getRangeEnd() {
		return rangeEnd;
	}
	public void setRangeEnd(String rangeEnd) {
		this.rangeEnd = rangeEnd;
	}
}
