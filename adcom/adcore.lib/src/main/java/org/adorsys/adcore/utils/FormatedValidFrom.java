package org.adorsys.adcore.utils;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class FormatedValidFrom {

	public static final String PATTERN = "yyyyMMddHHmmss";
	public static final String PATTERN_TZ = "yyyyMMddHHmmss Z";
	public static final String TZ = " +0000";
	
	public static String format(Date date){
		if(date == null) return null;
		return DateFormatUtils.formatUTC(date, PATTERN);
	}
	
	public static Date parse(String dateStr){
		try {
			return DateUtils.parseDate(dateStr+TZ, PATTERN_TZ);
		} catch (ParseException e) {
			throw new IllegalStateException(e);
		}
	}
}
