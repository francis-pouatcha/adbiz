package org.adorsys.adcore.utils;

import java.util.Date;

/**
 * A pair of date delimiting the start and the end of the time frame.
 * 
 * @author francis
 *
 */
public class Instant {

	/** The date. */
	private final Date date;
	
	/** The time zone. */
	private final String timeZone;
	
	/** The day str. */
	private final String dayStr;
	
	/** The week str. */
	private final String weekStr;
	
	/** The month str. */
	private final String monthStr;
	
	/** The year str. */
	private final String yearStr;

	/**
	 * Instantiates a new instant.
	 * 
	 * @param date
	 *            the date
	 * @param timeZone
	 *            the time zone
	 * @param dayStr
	 *            the day str
	 * @param weekStr
	 *            the week str
	 * @param monthStr
	 *            the month str
	 * @param yearStr
	 *            the year str
	 */
	public Instant(Date date, String timeZone, String dayStr, String weekStr,
			String monthStr, String yearStr) {
		super();
		this.date = date;
		this.timeZone = timeZone;
		this.dayStr = dayStr;
		this.weekStr = weekStr;
		this.monthStr = monthStr;
		this.yearStr = yearStr;
	}

	/**
	 * Gets the date.
	 * 
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Gets the time zone.
	 * 
	 * @return the time zone
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * Gets the day str.
	 * 
	 * @return the day str
	 */
	public String getDayStr() {
		return dayStr;
	}

	/**
	 * Gets the week str.
	 * 
	 * @return the week str
	 */
	public String getWeekStr() {
		return weekStr;
	}

	/**
	 * Gets the month str.
	 * 
	 * @return the month str
	 */
	public String getMonthStr() {
		return monthStr;
	}

	/**
	 * Gets the year str.
	 * 
	 * @return the year str
	 */
	public String getYearStr() {
		return yearStr;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Instant [date=" + date + ", timeZone=" + timeZone + ", dayStr="
				+ dayStr + ", weekStr=" + weekStr + ", monthStr=" + monthStr
				+ ", yearStr=" + yearStr + "]";
	}
	
	
}
