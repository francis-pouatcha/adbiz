package org.adorsys.adcshdwr.payementevent;

import java.math.BigDecimal;
import java.util.Date;

import org.adorsys.adcshdwr.jpa.CdrPymntMode;

public class PaymentEvent {

	private CdrPymntMode pymntMode;
    private BigDecimal amt;
    private BigDecimal rcvdAmt;
    private Date datetime;
    private String saleNbr;
    private String vchrNbr;
    private String pymntNbr;
    
    
	public PaymentEvent() {
		super();
	}
	
	public PaymentEvent(CdrPymntMode pymntMode, BigDecimal amt,
			BigDecimal rcvdAmt, Date datetime, String saleNbr) {
		super();
		this.pymntMode = pymntMode;
		this.amt = amt;
		this.rcvdAmt = rcvdAmt;
		this.datetime = datetime;
		this.saleNbr = saleNbr;
	}
	public PaymentEvent(CdrPymntMode pymntMode, BigDecimal amt,
			BigDecimal rcvdAmt, Date datetime, String saleNbr, String vchrNbr,
			String pymntNbr) {
		super();
		this.pymntMode = pymntMode;
		this.amt = amt;
		this.rcvdAmt = rcvdAmt;
		this.datetime = datetime;
		this.saleNbr = saleNbr;
		this.vchrNbr = vchrNbr;
		this.pymntNbr = pymntNbr;
	}

	public CdrPymntMode getPymntMode() {
		return pymntMode;
	}
	public void setPymntMode(CdrPymntMode pymntMode) {
		this.pymntMode = pymntMode;
	}
	
	public BigDecimal getRcvdAmt() {
		return rcvdAmt;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public void setRcvdAmt(BigDecimal rcvdAmt) {
		this.rcvdAmt = rcvdAmt;
	}

	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public String getSaleNbr() {
		return saleNbr;
	}
	public String getVchrNbr() {
		return vchrNbr;
	}

	public void setVchrNbr(String vchrNbr) {
		this.vchrNbr = vchrNbr;
	}

	public String getPymntNbr() {
		return pymntNbr;
	}

	public void setPymntNbr(String pymntNbr) {
		this.pymntNbr = pymntNbr;
	}

	public void setSaleNbr(String saleNbr) {
		this.saleNbr = saleNbr;
	}
}
