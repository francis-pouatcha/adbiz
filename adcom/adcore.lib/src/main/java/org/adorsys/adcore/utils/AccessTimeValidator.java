package org.adorsys.adcore.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class AccessTimeValidator {
	
	public static boolean check(String accessTime, String timeZone){
		Date now = new Date();
		if(StringUtils.isBlank(accessTime)) return true;
		// 1,2,3,4,5(06:30-12:30,13:00-19:30),6(07:30-12:30,16:00-19:30)
		String[] daysConfig = StringUtils.split(accessTime, ')');
		if(daysConfig==null || daysConfig.length==0) return false;
		
		Map<Integer, List<List<String>>> dayMap = new HashMap<Integer, List<List<String>>>();
		for (String daysList : daysConfig) {
			String listOfDays = StringUtils.substringBefore(daysList, "(").trim();
			String timeStr = StringUtils.substringAfter(daysList, "(");
			String[] split = timeStr.split(",");
			List<List<String>> times = new ArrayList<List<String>>();
			for (String time : split) {
				if(StringUtils.isNotBlank(time)){
					String[] split2 = time.trim().split("-");
					times.add(Arrays.asList(split2));
				}
			}
			String[] days = StringUtils.split(listOfDays, ',');
			for (String day : days) {
				if(StringUtils.isBlank(day)) continue;
				Integer dayInt = new Integer(day.trim());
				dayMap.put(dayInt, times);
			}
		}
		int dayOfWeek = CalendarUtil.getDayOfWeek(now, timeZone);
		List<List<String>> timeStrings = dayMap.get(dayOfWeek);
		if(timeStrings==null) return false;
		List<List<Date>> dates = CalendarUtil.getDate(timeZone, now, timeStrings);
		for (List<Date> list : dates) {
			if(list.size()==1){	
				Date date = list.get(0);
				if(now.after(date)) return true;
			}
			if(list.size()>1){
				Date fromDate = list.get(0);
				Date toDate = list.get(1);
				if(now.after(fromDate) && now.before(toDate)) return true;
			}
		}
		return false;
	}
}
