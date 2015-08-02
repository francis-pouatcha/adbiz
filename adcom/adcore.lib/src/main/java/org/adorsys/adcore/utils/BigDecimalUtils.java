package org.adorsys.adcore.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.adorsys.adcore.jpa.CoreCurrencyEnum;
import org.apache.commons.lang3.StringUtils;

public class BigDecimalUtils {
	
	public static final BigDecimal HUNDRED = new BigDecimal("100");
	public static final BigDecimal MINUS_ONE = new BigDecimal("-1");
	
	public static boolean isNullOrZero(BigDecimal bigDecimal){
		return bigDecimal==null || bigDecimal.compareTo(BigDecimal.ZERO)==0;
	}
	
	public static BigDecimal zeroIfNull(BigDecimal bigDecimal){
		if(bigDecimal==null) return BigDecimal.ZERO;
		return bigDecimal;
	}
	
	public static boolean numericEquals(BigDecimal a, BigDecimal b){
		if(isNullOrZero(a)) return isNullOrZero(b);
		if(isNullOrZero(b)) return false;
		return a.compareTo(b)==0;
	}
	public static boolean strictEquals(BigDecimal a, BigDecimal b){
		if(a==null && b!=null) return false;
		if(b==null && a!=null) return false;
		return numericEquals(a, b);
	}

	public static boolean greaterZero(BigDecimal a){
		if(isNullOrZero(a)) return false;
		return a.compareTo(BigDecimal.ZERO)>0;
	}
	
	public static BigDecimal sum(BigDecimal... sumands){
		BigDecimal result = BigDecimal.ZERO;
		for (BigDecimal sumand : sumands) {
			result = result.add(zeroIfNull(sumand));
		}
		return result;
	}
	
	public static BigDecimal subs(BigDecimal base,BigDecimal ... substracts) {
		base = zeroIfNull(base);
		for (BigDecimal bigDecimal : substracts) {
			base = FinancialOps.substract(base, zeroIfNull(bigDecimal));
		}
		return base;
	}

	public static BigDecimal ratePercentOfPartFromBase(BigDecimal part, BigDecimal base, RoundingMode roundingMode){
		if(isNullOrZero(base) || isNullOrZero(part)) return BigDecimal.ZERO;

		if(numericEquals(part,base)) return HUNDRED;
		
		BigDecimal result = zeroIfNull(part);

		return result.multiply(HUNDRED).divide(base, roundingMode);
	}

	public static BigDecimal basePercentOfRatePct(BigDecimal ratePct, BigDecimal base){
		if(isNullOrZero(base) || isNullOrZero(ratePct)) return BigDecimal.ZERO;
		BigDecimal result = zeroIfNull(base);

		if(numericEquals(ratePct,HUNDRED)) return result;
		
		result = result.multiply(ratePct).divide(HUNDRED);

		return result;
	}

	public static BigDecimal basePercentOfRatePct(BigDecimal ratePct, BigDecimal base, RoundingMode roundingMode){
		if(isNullOrZero(base) || isNullOrZero(ratePct)) return BigDecimal.ZERO;

		BigDecimal result = zeroIfNull(base);
		if(numericEquals(ratePct,HUNDRED)) return result;
		
		return result.multiply(ratePct).divide(HUNDRED, roundingMode);
	}
	
	public static BigDecimal basePercentOfRatePct(BigDecimal ratePct, BigDecimal base, String curr){
		if(isNullOrZero(base) || isNullOrZero(ratePct)) return BigDecimal.ZERO;
		if(StringUtils.isBlank(curr))curr=CoreCurrencyEnum.XAF.name();

		BigDecimal result = zeroIfNull(base);
		if(numericEquals(ratePct,HUNDRED)) return result;

		CoreCurrencyEnum currEnum = CoreCurrencyEnum.valueOf(curr);
		return result.multiply(ratePct).divide(HUNDRED, currEnum.getDcmlPos(), RoundingMode.HALF_EVEN);
	}
	
	public static BigDecimal amountWithTax(BigDecimal amtHT, BigDecimal vatRatePct){
		BigDecimal vat = basePercentOfRatePct(vatRatePct, amtHT, RoundingMode.HALF_EVEN);
		return sum(amtHT, vat);
	}
	
	public Map<String, BigDecimal> share(BigDecimal base, Map<String, BigDecimal> perctgs){
		int size = perctgs.size();
		Map<String, BigDecimal> result = new HashMap<String, BigDecimal>(size);
		Set<Entry<String,BigDecimal>> entrySet = perctgs.entrySet();
		for (Entry<String, BigDecimal> entry : entrySet) {
			BigDecimal ratePct = entry.getValue();
			BigDecimal value = basePercentOfRatePct(ratePct, base, RoundingMode.HALF_EVEN);
			result.put(entry.getKey(), value);
		}
		return result ;
	}
	
	public static BigDecimal negate(BigDecimal in){
		if(in==null) return BigDecimal.ZERO;
		if(BigDecimal.ZERO.compareTo(in)==0) return in;
		return MINUS_ONE.multiply(in);
	}
}
