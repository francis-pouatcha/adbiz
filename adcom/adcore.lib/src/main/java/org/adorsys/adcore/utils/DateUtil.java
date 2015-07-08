package org.adorsys.adcore.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author guymoyo
 *
 */
public class DateUtil {

	public final static String  DATE_FORMAT_SHORT = "dd-MM-yyyy";
	public final static String DATE_TIME_FORMAT="dd-MM-yyyy HH:mm";
	
	public static  String transform(Date validFrom,String pattern) {
		String validFromStr = format(validFrom, pattern);
		if(validFromStr == null){
			validFromStr = "-";
		}
		return validFromStr;
	}
	
	public static String  format(Date date , String patern){
		if(date ==null) return null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patern);
		String format = simpleDateFormat.format(date);
		return format ;
	}
}
