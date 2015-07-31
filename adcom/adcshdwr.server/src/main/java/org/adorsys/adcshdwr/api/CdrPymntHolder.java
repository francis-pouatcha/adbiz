/**
 * 
 */
package org.adorsys.adcshdwr.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adorsys.adcshdwr.jpa.CdrPymnt;
import org.adorsys.adcshdwr.jpa.CdrPymntItem;
import org.adorsys.adcshdwr.jpa.CdrPymntMode;

public class CdrPymntHolder {
	
	private BigDecimal amt;

	private BigDecimal rcvdAmt;
	
	private CdrPymntMode pymntMode;
	
	private String pymntNbr;
	
	private String invceNbr;
	
	private String vchrNbr;
	
	private CdrPymnt cdrPymnt;
	
	private List<CdrPymntItem> cdrPymntItems = new ArrayList<CdrPymntItem>();

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public BigDecimal getRcvdAmt() {
		return rcvdAmt;
	}

	public void setRcvdAmt(BigDecimal rcvdAmt) {
		this.rcvdAmt = rcvdAmt;
	}

	public CdrPymntMode getPymntMode() {
		return pymntMode;
	}

	public void setPymntMode(CdrPymntMode pymntMode) {
		this.pymntMode = pymntMode;
	}

	public String getPymntNbr() {
		return pymntNbr;
	}

	public void setPymntNbr(String pymntNbr) {
		this.pymntNbr = pymntNbr;
	}

	public String getInvceNbr() {
		return invceNbr;
	}

	public void setInvceNbr(String invceNbr) {
		this.invceNbr = invceNbr;
	}

	public String getVchrNbr() {
		return vchrNbr;
	}

	public void setVchrNbr(String vchrNbr) {
		this.vchrNbr = vchrNbr;
	}

	public List<CdrPymntItem> getCdrPymntItems() {
		return cdrPymntItems;
	}

	public void setCdrPymntItems(List<CdrPymntItem> cdrPymntItems) {
		this.cdrPymntItems = cdrPymntItems;
	}

	public CdrPymnt getCdrPymnt() {
		return cdrPymnt;
	}

	public void setCdrPymnt(CdrPymnt cdrPymnt) {
		this.cdrPymnt = cdrPymnt;
	}

	
}
