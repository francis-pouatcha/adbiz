package org.adorsys.adcore.utils;

import java.math.BigDecimal;
/**
 * 
 * @author guymoyo
 *
 */
public  class RoundAmount {
		
	private BigDecimal amount;
	private BigDecimal roundDiscount;
	
	
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRoundDiscount() {
		return roundDiscount;
	}

	public void setRoundDiscount(BigDecimal roundDiscount) {
		this.roundDiscount = roundDiscount;
	}


	public void roundIt(BigDecimal amount){
		this.amount = amount;
		this.roundDiscount = BigDecimal.ZERO;
		BigDecimal remaind;
		Boolean isMultiple = false;
        while (!isMultiple) {
        	remaind = this.amount.remainder(BigDecimal.valueOf(5));
            if ((remaind.compareTo(BigDecimal.ZERO) == 0)) {
                isMultiple = true;
            }
            else {
            	this.roundDiscount=this.roundDiscount.add(BigDecimal.ONE);
            	this.amount=this.amount.add(BigDecimal.ONE);
            }
        }
	}
	
}

	