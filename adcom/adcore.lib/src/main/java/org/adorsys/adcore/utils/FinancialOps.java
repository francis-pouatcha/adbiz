package org.adorsys.adcore.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.adorsys.adcore.jpa.CoreCurrencyEnum;
import org.apache.commons.lang3.StringUtils;

public class FinancialOps {

	public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
	
	public static BigDecimal amtFromPrct(BigDecimal base, BigDecimal prct, String curr){
		if(StringUtils.isBlank(curr))curr=CoreCurrencyEnum.XAF.name();
		if(base==null)base = BigDecimal.ZERO;
		if(prct==null)prct = BigDecimal.ZERO;
		CoreCurrencyEnum currEnum = CoreCurrencyEnum.valueOf(curr);
		return base.multiply(prct).divide(ONE_HUNDRED, currEnum.getDcmlPos(), RoundingMode.HALF_EVEN);
	}

	public static BigDecimal prctFromAmt(BigDecimal base, BigDecimal amt, String curr){
		if(StringUtils.isBlank(curr))curr=CoreCurrencyEnum.XAF.name();
		if(amt==null)amt = BigDecimal.ZERO;
		if(base==null) return BigDecimal.ZERO;
		//return base.multiply(ONE_HUNDRED).divide(base, 4, RoundingMode.HALF_EVEN);
		return amt.multiply(ONE_HUNDRED).divide(base, 4, RoundingMode.HALF_EVEN);
	}
	
	public static BigDecimal substract(BigDecimal base, BigDecimal subtrahend, String curr){
		if(base==null)base = BigDecimal.ZERO;
		if(subtrahend==null)subtrahend = BigDecimal.ZERO;
		return base.subtract(subtrahend);
	}
	
	public static BigDecimal substract(BigDecimal base, BigDecimal subtrahend){
		if(base==null)base = BigDecimal.ZERO;
		if(subtrahend==null)subtrahend = BigDecimal.ZERO;
		return base.subtract(subtrahend);
	}

	public static BigDecimal add(BigDecimal base, BigDecimal augend,String curr) {
		if(base==null)base = BigDecimal.ZERO;
		if(augend==null)augend = BigDecimal.ZERO;
		return base.add(augend);
	}

	public static BigDecimal qtyTmsPrice(BigDecimal qty, BigDecimal price, String curr){
		if(StringUtils.isBlank(curr))curr=CoreCurrencyEnum.XAF.name();
		if(qty==null)qty = BigDecimal.ZERO;
		if(price==null)price = BigDecimal.ZERO;
//		CoreCurrencyEnum currEnum = CoreCurrencyEnum.valueOf(curr);
		return qty.multiply(price);
	}
}
