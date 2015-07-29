package org.adorsys.adcshdwr.api;

import java.math.BigDecimal;

class AmtHolder {
	BigDecimal netSalesAmt = BigDecimal.ZERO;
	BigDecimal vatAmount = BigDecimal.ZERO;
	BigDecimal netSPPreTax = BigDecimal.ZERO;
	BigDecimal netSPTaxIncl = BigDecimal.ZERO;
	BigDecimal grossSPPreTax = BigDecimal.ZERO;
	BigDecimal rebate = BigDecimal.ZERO;
	/**
	 * Create a new AmtHolder.
	 */
	public AmtHolder() {
	}
}