/**
 * 
 */
package org.adorsys.adcatal.rest;

import org.apache.commons.lang3.StringUtils;

/**
 * @author boriswaguia
 *
 */
public class AlphabetUtil {
	private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
	
	public static String extractRange(String start,String end) {
		if(StringUtils.isBlank(start)) {
			start = extractFirstChar(alphabet);
		}
		if(StringUtils.isBlank(end)) {
			end = extractLastChar(alphabet);//get the last char of the string.
		}
		String substring = StringUtils.substringBetween(alphabet, start, end);
		substring = start.concat(substring).concat(end);
		return substring;
	}

	private static String extractLastChar(String src) {
		if(StringUtils.isBlank(src)) return "";
		return extractChar(src, src.length());
	}

	private static String extractChar(String src, int length) {
		return String.valueOf(src.toCharArray()[length]);
	}
	
	private static String extractFirstChar(String src) {
		return extractChar(src, 0);
	}
	
	public static char[] extractToChar(String start, String end) {
		String range = extractRange(start, end);
		return range.toCharArray();
	}
}
