package org.adorsys.adcore.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class CalendarUtil {

	/** The Constant DAY_PATTERN. */
	private static final String DAY_PATTERN = "yyyyMMdd";
	
	/** The Constant MONTH_PATTERN. */
	private static final String MONTH_PATTERN = "yyyyMM";
	
	/** The Constant YEAR_PATTERN. */
	private static final String YEAR_PATTERN = "yyyy";
	
	/** The Constant WEEK_PATTERN. */
	private static final String WEEK_PATTERN = "w";

	/**
	 * Gets the instant.
	 * 
	 * @param timeZoneId
	 *            the time zone id
	 * @param date
	 *            the date
	 * @param closing
	 *            the closing
	 * @return the instant
	 */
	public static Instant getInstant(String timeZoneId, Date date, String closing){
		int[] hoursMins = parse(closing);
		
		TimeZone tz = TimeZone.getTimeZone(timeZoneId);
		GregorianCalendar calendar = new GregorianCalendar(tz);
		calendar.setTime(date);

		Calendar dayStart = DateUtils.truncate(calendar, Calendar.DATE);
		Date endDay = DateUtils.addHours(dayStart.getTime(), hoursMins[0]);
		endDay = DateUtils.addMinutes(endDay, hoursMins[1]);
		if(endDay.before(date)) endDay = DateUtils.addDays(endDay, 1);
		calendar.setTime(endDay);
		String dayStr = DateFormatUtils.format(calendar, DAY_PATTERN);
		String weekStr = DateFormatUtils.format(calendar, WEEK_PATTERN);
		String monthStr = DateFormatUtils.format(calendar, MONTH_PATTERN);
		String yearStr = DateFormatUtils.format(calendar, YEAR_PATTERN);
		
		return new Instant(endDay, timeZoneId, dayStr, weekStr, monthStr, yearStr);
	}
	private static TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("Europe/London");
	public static String getDayStr(Date date, String timeZoneId){
		TimeZone tz = StringUtils.isBlank(timeZoneId)?DEFAULT_TIMEZONE:TimeZone.getTimeZone(timeZoneId);
		GregorianCalendar calendar = new GregorianCalendar(tz);
		calendar.setTime(date);
		return DateFormatUtils.format(calendar, DAY_PATTERN);
	}
	
	public static boolean isSameDay(Date d, Date o){
		if(d==o) return true;
		if(d==null || o==null) return false;
		return DateUtils.isSameDay(d,o);
	}

	public static boolean isSameInstant(Date d, Date o){
		if(d==o) return true;
		if(d==null || o==null) return false;
		return DateUtils.isSameInstant(d, o);
	}
	
	/**
	 * Parses the.
	 * 
	 * @param closing
	 *            the closing
	 * @return the int[]
	 */
	public static int[] parse(String closing){
		String[] split = StringUtils.split(closing, ":");
		int hours = Integer.parseInt(split[0]);
		int minutes = Integer.parseInt(split[1]);
		return new int[]{hours, minutes};
	}
	
	/**
	 * Gets the previous day string.
	 * 
	 * @param dayString
	 *            the day string
	 * @return the previous day string
	 */
	public static String getPreviousDayString(String dayString){
		try {
			Date date = DateUtils.parseDate(dayString, DAY_PATTERN);
			date = DateUtils.addDays(date, -1);
			return DateFormatUtils.format(date, DAY_PATTERN);
		} catch (ParseException e) {
			throw new IllegalStateException (e);
		}
	}
	
	/**
	 * Gets the start day.
	 * 
	 * @return the start day
	 */
	public static List<List<Date>> getDate(String timeZoneId, Date date, List<List<String>> timeStrings){
		List<List<Date>> result = new ArrayList<List<Date>>();
		TimeZone tz = TimeZone.getTimeZone(timeZoneId);
		GregorianCalendar calendar = new GregorianCalendar(tz);
		calendar.setTime(date);
		for (List<String> list : timeStrings) {
			ArrayList<Date> arrayList = new ArrayList<Date>();
			for (String hourMinStr : list) {
				int[] hourMin = parse(hourMinStr);

				Calendar timeStartBase = DateUtils.truncate(calendar, Calendar.DATE);
				
				Date timeStartDate = DateUtils.addHours(timeStartBase.getTime(), hourMin[0]);
				timeStartDate = DateUtils.addMinutes(timeStartDate, hourMin[1]);
				arrayList.add(timeStartDate);
			}
			result.add(arrayList);
		}
		return result;
	}
	
	public static int getDayOfWeek(Date date, String timeZoneId){
		TimeZone tz = TimeZone.getTimeZone(timeZoneId);
		GregorianCalendar calendar = new GregorianCalendar(tz);
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return convert(dayOfWeek);
	}
	
	private static int convert(int dayOfWeek){
		switch (dayOfWeek) {
		case Calendar.MONDAY:
			return 1;
		case Calendar.TUESDAY:
			return 2;
		case Calendar.WEDNESDAY:
			return 3;
		case Calendar.THURSDAY:
			return 4;
		case Calendar.FRIDAY:
			return 5;
		case Calendar.SATURDAY:
			return 6;
		default:
			return 0;
		}
	}
}
