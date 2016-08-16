/*
 * Copyright(c) 2016 Mamoru Asagami. All rights reserved.
 * 
*/

package sophie.net.pac;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import static sophie.net.pac.TraceablePacFunctions.quote;
import static sophie.net.pac.TraceablePacFunctions.toStringFuncall;

public class PacFunctions {
	static final Pattern numberPattern = Pattern.compile("[+-]?\\d+");
	static final HashMap<String, Integer> monthMap;
	static final HashMap<String, Integer> weekMap;
	static Date today;
	static String myIpAddress;
	static HashMap<String, String> hostMap;
	
	static {
		monthMap = new HashMap<String, Integer>();
		String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
		for(int i = 0; i < months.length; i++) {
			monthMap.put(months[i], i);
		}
		weekMap = new HashMap<String, Integer>();
		String[] days = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
		for(int i = 0; i < days.length; i++) {
			weekMap.put(days[i], i);
		}
		
	}
	
	private static Calendar getCalendar(String timeZone) {
		final Calendar calendar;
		if(timeZone != null) {
			if(!timeZone.equals("GMT")) {
				System.err.println("Warning: TimeZone: " + quote(timeZone) + " was specified, but the specification only allows \"GMT\".");
			}
			calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
		} else {
			calendar = Calendar.getInstance();
		}
		calendar.setTime(today);
		return calendar;
	}
	
	private static boolean isYear(String year) {
		try {
			int value = Integer.valueOf(year);
			return value >= 1900;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	private static int getYear(String year) {
		return Integer.valueOf(year);
	}
	
	private static boolean isMonth(String month) {
		return monthMap.containsKey(month.toUpperCase());
	}
	
	private static int getMonth(String month) {
		String upperCaseMonth = month.toUpperCase();
		if(!month.equals(upperCaseMonth)) {
			System.out.println("Warning: Use " + quote(upperCaseMonth) + " instead of " + quote(month) + ".");
		}
		return monthMap.get(upperCaseMonth);
	}
	
	private static boolean isWeekday(String weekday) {
		return weekMap.containsKey(weekday.toUpperCase());
	}
	
	private static int getWeekday(String weekday) {
		String upperCaseWeekday = weekday.toUpperCase();
		if(!weekday.equals(upperCaseWeekday)) {
			System.out.println("Warning: Use " + quote(upperCaseWeekday) + " instead of " + quote(weekday) + ".");
		}
		return weekMap.get(upperCaseWeekday);
	}
	
	private static boolean isDay(String day) {
		try {
			int value = Integer.valueOf(day);
			return value >= 1 && value <= 31;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	private static boolean isTimeZone(String zone) {
		return !(numberPattern.matcher(zone).matches() || isMonth(zone) || isWeekday(zone));
	}
	
	private static int getDay(String day) {
		return Integer.valueOf(day);
	}
	
	private static boolean isHour(String hour) {
		try {
			int h = Integer.valueOf(hour);
			return 0 <= h && h < 24;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	private static boolean isMinSec(String s) {
		try {
			int n = Integer.valueOf(s);
			return 0 <= n && n < 60;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	private static int getHourMinSec(String s) {
		return Integer.valueOf(s);
	}
	
	private static boolean dayRange(int day1, int day2, String timeZone) {
		// System.out.println(toStringFuncall("dayRange", Integer.toString(day1), Integer.toString(day2), timeZone));
		Calendar cal = getCalendar(timeZone);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return day1 <= day && day <= day2;
	}
	
	private static boolean monthRange(int month1, int month2, String timeZone) {
		// System.out.println(toStringFuncall("monthRange", Integer.toString(month1), Integer.toString(month2), timeZone));
		Calendar cal = getCalendar(timeZone);
		int month = cal.get(Calendar.MONTH);
		int max = month2 - month1;
		if(max < 0) {
			max += 12;
		}
		int delta = month - month1;
		if(delta < 0) {
			delta += 12;
		}
		return delta <= max;
	}

	private static boolean yearRange(int year1, int year2, String timeZone) {
		// System.out.println(toStringFuncall("yearRange", Integer.toString(year1), Integer.toString(year2), timeZone));
		Calendar cal = getCalendar(timeZone);
		int year = cal.get(Calendar.YEAR);
		return year1 <= year && year <= year2;
	}

	private static boolean monthYearRange(int month1, int year1, int month2, int year2, String timeZone) {
		// System.out.println(toStringFuncall("monthYearRange", Integer.toString(month1), Integer.toString(year1), Integer.toString(month2), Integer.toString(year2), timeZone));
		Calendar cal = getCalendar(timeZone);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int monthYear1 = year1 * 12 + month1;
		int monthYear2 = year2 * 12 + month2;
		int monthYear = year * 12 + month;
		return monthYear1 <= monthYear && monthYear <= monthYear2;
	}

	private static boolean dayMonthRange(int day1, int month1, int day2, int month2, String timeZone) {
		// System.out.println(toStringFuncall("dayMonthRange", Integer.toString(day1), Integer.toString(month1), Integer.toString(day2), Integer.toString(month2), timeZone));
		Calendar cal = getCalendar(timeZone);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int dayMonth1 = month1 * 32 + day1;
		int dayMonth2 = month2 * 32 + day2;
		int dayMonth = month * 32 + day;
		return dayMonth1 <= dayMonth && dayMonth <= dayMonth2;
	}
	
	private static boolean dayMonthYearRange(int day1, int month1, int year1, int day2, int month2, int year2, String timeZone) {
		// System.out.println(toStringFuncall("dayMonthYearRange", Integer.toString(day1), Integer.toString(month1), Integer.toString(year1), Integer.toString(day2), Integer.toString(month2), Integer.toString(year2), timeZone));
		Calendar cal = getCalendar(timeZone);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int dayMonthYear1 = (year1 + month1 * 12) * 32 + day1;
		int dayMonthYear2 = (year2 + month2 * 12) * 32 + day2;
		int dayMonthYear = (year + month * 12) * 32 + day;
		return dayMonthYear1 <= dayMonthYear && dayMonthYear <= dayMonthYear2;
	}
	
	private static boolean hourRange(int hour1, int hour2, String timeZone) {
		// System.out.println(toStringFuncall("hourRange", Integer.toString(hour1), Integer.toString(hour2), timeZone));
		Calendar cal = getCalendar(timeZone);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		return hour1 <= hour && hour <= hour2;
	}
	
	private static boolean hourMinRange(int hour1, int min1, int hour2, int min2, String timeZone) {
		// System.out.println(toStringFuncall("hourMinRange", Integer.toString(hour1), Integer.toString(min1), Integer.toString(hour2), Integer.toString(min2), timeZone));
		Calendar cal = getCalendar(timeZone);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int hourMin = hour * 60 + min;
		int hourMin1 = hour1 * 60 + min1;
		int hourMin2 = hour2 * 60 + min2;
		return hourMin1 <= hourMin && hourMin <= hourMin2;
	}
	
	private static boolean hourMinSecRange(int hour1, int min1, int sec1, int hour2, int min2, int sec2, String timeZone) {
		// System.out.println(toStringFuncall("hourMinSecRange", Integer.toString(hour1), Integer.toString(min1), Integer.toString(sec1), Integer.toString(hour2), Integer.toString(min2), Integer.toString(sec2), timeZone));
		Calendar cal = getCalendar(timeZone);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		int hourMinSec1 = (hour1 * 60 + min1) * 60 + sec1;
		int hourMinSec2 = (hour2 * 60 + min2) * 60 + sec2;
		int hourMinSec = (hour * 60 + min) * 60 + sec;
		return hourMinSec1 <= hourMinSec && hourMinSec <= hourMinSec2;
	}
	
	private static boolean weekdayRange(int day1, int day2, String timeZone) {
		// System.out.println(toStringFuncall("weekdayRange", Integer.toString(day1), Integer.toString(day2), timeZone));
		Calendar cal = getCalendar(timeZone);
		int day = cal.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
		int max = day2 - day1;
		if(max < 0) {
			max += 7;
		}
		int delta = day - day1;
		if(delta < 0) {
			delta += 7;
		}
		//System.out.println("day: " + day + " max: " + max + " delata: " + delta);
		return delta <= max;
	}
	
	public static void captureLine(String stack) {
		// Nothing to do
	}
	
	public static void alert(String message) {
		//new RuntimeException().printStackTrace();
		JOptionPane.showMessageDialog(null, message, PacFunctions.class.getSimpleName() + " - " + "Alert", JOptionPane.WARNING_MESSAGE);
	}
	
	public static boolean dateRange(String arg1) {
		// dateRange(day)
		// dateRange(month)
		// dateRange(year)
		
		if(isDay(arg1)) {
			int day = getDay(arg1);
			return dayRange(day, day, null);
		} else if(isMonth(arg1)) {
			int month = getMonth(arg1);
			return monthRange(month, month, null);
		} else if(isYear(arg1)) {
			int year = getYear(arg1);
			return yearRange(year, year, null);
		} else {
			throw new IllegalArgumentException(toStringFuncall("dateRange", arg1));
		}
	}

	public static boolean dateRange(String arg1, String arg2) {
		// dateRange(day, GMT)
		// dateRange(month, GMT)
		// dateRange(year, GMT)
		// dateRange(day, day)
		// dateRange(day, month)
		// dateRange(month, month)
		// dateRange(month, year)
		// dateRange(year, year)
		
		if(isTimeZone(arg2)) {
			String zone = arg2;
			if(isDay(arg1)) {
				int day = getDay(arg1);
				return dayRange(day, day, zone);
			} else if(isMonth(arg1)) {
				int month = getMonth(arg1);
				return monthRange(month, month, zone);
			} else if(isYear(arg1)) {
				int year = getYear(arg1);
				return yearRange(year, year, zone);
			} else {
				// fall through
			}
		} else {
			if(isDay(arg1)) {
				int day = getDay(arg1);
				if(isDay(arg2)) {
					int day2 = getDay(arg2);
					return dayRange(day, day2, null);
				} else if(isMonth(arg2)) {
					int month = getMonth(arg2);
					return dayMonthRange(day, month, day, month, null);
				} else {
					// fall through
				}
			} else if(isMonth(arg1)) {
				int month = getMonth(arg1);
				if(isMonth(arg2)) {
					int month2 = getMonth(arg2);
					return monthRange(month, month2, null);
				} else if(isYear(arg2)) {
					int year = getYear(arg2);
					return monthYearRange(month, year, month, year, null);
				} else {
					// fall through
				}
			} else if(isYear(arg1)) {
				int year = getYear(arg1);
				if(isYear(arg2)) {
					int year2 = getYear(arg2);
					return yearRange(year, year2, null);
				} else {
					// fall through
				}
			} else {
				// fall through
			}
		}
		throw new IllegalArgumentException(toStringFuncall("dateRange", arg1, arg2));
	}

	public static boolean dateRange(String arg1, String arg2, String arg3) {
		// dateRange(day, day, GMT)
		// dateRange(day, month, GMT)
		// dateRange(month, month, GMT)
		// dateRange(month, year, GMT)
		// dateRange(year, year, GMT)
		// dateRange(day, month, year)
		
		if(isTimeZone(arg3)) {
			String zone = arg3;
			if(isDay(arg1)) {
				int day = getDay(arg1);
				if(isDay(arg2)) {
					int day2 = getDay(arg2);
					return dayRange(day, day2, zone);
				} else if(isMonth(arg2)) {
					int month = getMonth(arg2);
					return dayMonthRange(day, month, day, month, zone);
				} else {
					// fall through
				}
			} else if(isMonth(arg1)) {
				int month = getMonth(arg1);
				if(isMonth(arg2)) {
					int month2 = getMonth(arg2);
					return monthRange(month, month2, zone);
				} else if(isYear(arg2)) {
					int year = getYear(arg2);
					return monthYearRange(month, year, month, year, zone);
				} else {
					// fall through
				}
			} else if(isYear(arg1)) {
				int year = getYear(arg1);
				if(isYear(arg2)) {
					int year2 = getYear(arg2);
					return yearRange(year, year2, zone);
				} else {
					// fall through
				}
			} else {
				// fall through
			}
		} else {
			if(isDay(arg1) && isMonth(arg2) && isYear(arg3)) {
				int day = getDay(arg1);
				int month = getMonth(arg2);
				int year = getYear(arg3);
				return dayMonthYearRange(day, month, year, day, month, year, null);
			} else {
				// fall through
			}
		}
		throw new IllegalArgumentException(toStringFuncall("dateRange", arg1, arg2, arg3));
	}

	public static boolean dateRange(String arg1, String arg2, String arg3, String arg4) {
		// dateRange(day, month, year, GMT)
		// dateRange(day, month, day, month)
		// dateRange(month, year, month, year)
		
		if(isTimeZone(arg4)) {
			String zone = arg4;
			if(isDay(arg1) && isMonth(arg2) && isYear(arg3)) {
				int day = getDay(arg1);
				int month = getMonth(arg2);
				int year = getYear(arg3);
				return dayMonthYearRange(day, month, year, day, month, year, zone);
			} else {
				// fall through
			}
		} else {
			if(isDay(arg1) && isMonth(arg2) && isDay(arg3) && isMonth(arg4)) {
				int day1 = getDay(arg1);
				int month1 = getMonth(arg2);
				int day2 = getDay(arg3);
				int month2 = getMonth(arg4);
				return dayMonthRange(day1, month1, day2, month2, null);
			} else if(isMonth(arg1) && isYear(arg2) && isMonth(arg3) && isYear(arg4)) {
				int month1 = getMonth(arg1);
				int year1 = getYear(arg2);
				int month2 = getMonth(arg3);
				int year2 = getYear(arg4);
				return monthYearRange(month1, year1, month2, year2, null);
			} else {
				// fall through
			}
		}
		throw new IllegalArgumentException(toStringFuncall("dateRange", arg1, arg2, arg3, arg4));
	}

	public static boolean dateRange(String arg1, String arg2, String arg3, String arg4, String arg5) {
		// dateRange(day, month, day, month, GMT)
		// dateRange(month, year, month, year, GMT)
		
		if(isTimeZone(arg5)) {
			String zone = arg5;
			if(isDay(arg1) && isMonth(arg2) && isDay(arg3) && isMonth(arg4)) {
				int day1 = getDay(arg1);
				int month1 = getMonth(arg2);
				int day2 = getDay(arg3);
				int month2 = getMonth(arg4);
				return dayMonthRange(day1, month1, day2, month2, zone);
			} else if(isMonth(arg1) && isYear(arg2) && isMonth(arg3) && isYear(arg4)) {
				int month1 = getMonth(arg1);
				int year1 = getYear(arg2);
				int month2 = getMonth(arg3);
				int year2 = getYear(arg4);
				return monthYearRange(month1, year1, month2, year2, zone);
			} else {
				// fall through
			}
		} else {
			// fall through
		}
		throw new IllegalArgumentException(toStringFuncall("dateRange", arg1, arg2, arg3, arg4, arg5));
	}

	public static boolean dateRange(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
		// dateRange(day, month, year, day, month, year)
		
		if(isDay(arg1) && isMonth(arg2) && isYear(arg3) && isDay(arg4) && isMonth(arg5) && isYear(arg6)) {
			int day1 = getDay(arg1);
			int month1 = getMonth(arg2);
			int year1 = getYear(arg3);
			int day2 = getDay(arg4);
			int month2 = getMonth(arg5);
			int year2 = getYear(arg6);
			return dayMonthYearRange(day1, month1, year1, day2, month2, year2, null);
		}
		throw new IllegalArgumentException(toStringFuncall("dateRange", arg1, arg2, arg3, arg4, arg5, arg6));
	}

	public static boolean dateRange(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7) {
		// dateRange(day, month, year, day, month, year, GMT)
		
		if(isDay(arg1) && isMonth(arg2) && isYear(arg3) && isDay(arg4) && isMonth(arg5) && isYear(arg6)) {
			int day1 = getDay(arg1);
			int month1 = getMonth(arg2);
			int year1 = getYear(arg3);
			int day2 = getDay(arg4);
			int month2 = getMonth(arg5);
			int year2 = getYear(arg6);
			String zone = arg7;
			return dayMonthYearRange(day1, month1, year1, day2, month2, year2, zone);
		}
		throw new IllegalArgumentException(toStringFuncall("dateRange", arg1, arg2, arg3, arg4, arg5, arg6, arg7));
	}

	public static boolean dnsDomainIs(String host, String domain) {
		host = host.toLowerCase();
		domain = domain.toLowerCase();
		return host.endsWith(domain);
		/*
		if(host.equals(domain)) {
			return true;
		} else if(domain.startsWith(".") && host.endsWith(domain)) {
			return true;
		} else if(!domain.startsWith(".") && host.endsWith("." + domain)) {
			return true;
		}
		*/
	}

	public static int dnsDomainLevels(String host) {
		int levels = 0;
		
		for(char c: host.toCharArray()) {
			if(c == '.') {
				levels++;
			}
		}
		return levels;
	}	
	
	public static String dnsResolve(String host) {
		if(hostMap != null && hostMap.containsKey(host)) {
			String address = hostMap.get(host);
			return address.equals("null")? "0.0.0.0": address;
		} else {
			try {
				InetAddress ina = InetAddress.getByName(host);
				return ina.getHostAddress();
			} catch(UnknownHostException e) {
				return "0.0.0.0";
			}
		}
	}

	public static boolean isInNet(String host, String pattern, String mask) {
		try {
			InetAddress hostIna = InetAddress.getByName(host);
			InetAddress patternIna = InetAddress.getByName(pattern);
			InetAddress maskIna = InetAddress.getByName(mask);
			byte[] hostAddress = hostIna.getAddress();
			byte[] patternAddress = patternIna.getAddress();
			byte[] maskAddress = maskIna.getAddress();
			
			if(patternIna.getAddress().length != maskIna.getAddress().length) {
				throw new IllegalArgumentException("pattern and mask network address conflict: " + pattern + " " + mask);
			}
			if(hostIna.getAddress().length != patternIna.getAddress().length) {
				throw new IllegalArgumentException("host and pattern network address conflict: " + host + " " + pattern);
			}
			int i = 0;
			for(i = 0; i < hostAddress.length; i++) {
				int h = hostAddress[i] & 0xff;
				int m = maskAddress[i] & 0xff;
				int p = patternAddress[i] & 0xff;
				if((h & m) != (p & m)) {
					break;
				}
			}
			return i == hostAddress.length;
		} catch(UnknownHostException e) {
			return false;
		}
	}
	
	public static boolean isPlainHostName(String host) {
		  return host.indexOf('.') < 0;
	}

	public static boolean isResolvable(String host) {
		if(hostMap != null && hostMap.containsKey(host)) {
			String address = hostMap.get(host);
			return (address.equals("null") || address.equals("0.0.0.0"))? false : true;
		} else {
			try {
				InetAddress ina = InetAddress.getByName(host);
				return true;
			} catch(UnknownHostException e) {
				return false;
			}
		}
	}
	
	public static boolean localHostOrDomainIs(String host, String hostdom) {
		// Is true if the hostname matches exactly the specified hostname,
		// or if there is no domain name part in the hostname, but the unqualified hostname matches.
		String h = host.toLowerCase();
		String hd = hostdom.toLowerCase();
		return (h.indexOf('.') >= 0)? h.equals(hd): hd.startsWith(h + ".");
	}
	
	
	public static String myIpAddress() {
		if(myIpAddress != null) {
			return myIpAddress;
		} else {
			try {
				return InetAddress.getLocalHost().getHostAddress();
			} catch(UnknownHostException e) {
				return "127.0.0.1";
			}
		}
	}
	
	public static boolean shExpMatch(String str, String pattern) {
		pattern = pattern.replace(".", "\\.");
		pattern = pattern.replace("?", ".");
		pattern = pattern.replace("*", ".*");
		return str.matches(pattern);
	}
	
	public static boolean timeRange(String arg1) {
		// timeRange(hour)
		
		if(isHour(arg1)) {
			int hour = getHourMinSec(arg1);
			return hourRange(hour, hour, null);
		}
		throw new IllegalArgumentException(toStringFuncall("timeRange", arg1));
	}

	public static boolean timeRange(String arg1, String arg2) {
		// timeRange(hour, GMT)
		// timeRange(hour, hour)
		
		if(isTimeZone(arg2)) {
			String zone = arg2;
			if(isHour(arg1)) {
				int hour = getHourMinSec(arg1);
				return hourRange(hour, hour, zone);
			}
		} else {
			if(isHour(arg1) && isHour(arg2)) {
				int hour1 = getHourMinSec(arg1);
				int hour2 = getHourMinSec(arg2);
				return hourRange(hour1, hour2, null);
			}
		}
		throw new IllegalArgumentException(toStringFuncall("timeRange", arg1, arg2));
	}

	public static boolean timeRange(String arg1, String arg2, String arg3) {
		// timeRange(hour, hour, GMT)
		
		if(isHour(arg1) && isHour(arg2) && isTimeZone(arg3)) {
			int hour1 = getHourMinSec(arg1);
			int hour2 = getHourMinSec(arg2);
			String zone = arg3;
			return hourRange(hour1, hour2, zone);
		}
		throw new IllegalArgumentException(toStringFuncall("timeRange", arg1, arg2, arg3));
	}

	public static boolean timeRange(String arg1, String arg2, String arg3, String arg4) {
		// timeRange(hour, min, hour, min)
		
		if(isHour(arg1) && isMinSec(arg2) && isHour(arg3) && isMinSec(arg4)) {
			int hour1 = getHourMinSec(arg1);
			int min1 = getHourMinSec(arg2);
			int hour2 = getHourMinSec(arg3);
			int min2 = getHourMinSec(arg4);
			return hourMinRange(hour1, min1, hour2, min2, null);
		}
		throw new IllegalArgumentException(toStringFuncall("timeRange", arg1, arg2, arg3, arg4));
	}

	public static boolean timeRange(String arg1, String arg2, String arg3, String arg4, String arg5) {
		// timeRange(hour, min, hour, min, GMT)
		
		if(isHour(arg1) && isMinSec(arg2) && isHour(arg3) && isMinSec(arg4) && isTimeZone(arg5)) {
			int hour1 = getHourMinSec(arg1);
			int min1 = getHourMinSec(arg2);
			int hour2 = getHourMinSec(arg3);
			int min2 = getHourMinSec(arg4);
			String zone = arg5;
			return hourMinRange(hour1, min1, hour2, min2, zone);
		}
		throw new IllegalArgumentException(toStringFuncall("timeRange", arg1, arg2, arg3, arg4, arg5));
	}

	public static boolean timeRange(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
		// timeRange(hour, min, sec, hour, min, sec)
		
		if(isHour(arg1) && isMinSec(arg2) && isMinSec(arg3) && isHour(arg4) && isMinSec(arg5) && isMinSec(arg6)) {
			int hour1 = getHourMinSec(arg1);
			int min1 = getHourMinSec(arg2);
			int sec1 = getHourMinSec(arg3);
			int hour2 = getHourMinSec(arg4);
			int min2 = getHourMinSec(arg5);
			int sec2 = getHourMinSec(arg6);
			return hourMinSecRange(hour1, min1, sec1, hour2, min2, sec2, null);
		}
		throw new IllegalArgumentException(toStringFuncall("timeRange", arg1, arg2, arg3, arg4, arg5, arg6));
	}

	public static boolean timeRange(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7) {
		// timeRange(hour, min, sec, hour, min, sec, GMT)
		
		if(isHour(arg1) && isMinSec(arg2) && isMinSec(arg3) && isHour(arg4) && isMinSec(arg5) && isMinSec(arg6) && isTimeZone(arg7)) {
			int hour1 = getHourMinSec(arg1);
			int min1 = getHourMinSec(arg2);
			int sec1 = getHourMinSec(arg3);
			int hour2 = getHourMinSec(arg4);
			int min2 = getHourMinSec(arg5);
			int sec2 = getHourMinSec(arg6);
			String zone = arg7;
			return hourMinSecRange(hour1, min1, sec1, hour2, min2, sec2, zone);
		}
		throw new IllegalArgumentException(toStringFuncall("timeRange", arg1, arg2, arg3, arg4, arg5, arg6, arg7));
	}
	
	public static boolean weekdayRange(String arg1) {
		// weekdayRange(weekday)
		
		if(isWeekday(arg1)) {
			int day = getWeekday(arg1);
			return weekdayRange(day, day, null);
		}
		throw new IllegalArgumentException(toStringFuncall("timeRange", arg1));
	}

	public static boolean weekdayRange(String arg1, String arg2) {
		// weekdayRange(weekday, GMT)
		// weekdayRange(weekday, weekday)
		
		if(isTimeZone(arg2)) {
			String zone = arg2;
			if(isWeekday(arg1)) {
				int day = getWeekday(arg1);
				return weekdayRange(day, day, zone);
			}
		} else {
			if(isWeekday(arg1) && isWeekday(arg2)) {
				int day1 = getWeekday(arg1);
				int day2 = getWeekday(arg2);
				return weekdayRange(day1, day2, null);
			}
		}
		throw new IllegalArgumentException(toStringFuncall("timeRange", arg1, arg2));
	}

	public static boolean weekdayRange(String arg1, String arg2, String arg3) {
		// weekdayRange(weekday, weekday, GMT)
		
		if(isWeekday(arg1) && isWeekday(arg2) && isTimeZone(arg3)) {
			int day1 = getWeekday(arg1);
			int day2 = getWeekday(arg2);
			String zone = arg3;
			return weekdayRange(day1, day2, zone);
		}
		throw new IllegalArgumentException(toStringFuncall("timeRange", arg1, arg2, arg3));
	}

	public static void setToday(Date today) {
		PacFunctions.today = today;
	}
	
	public static void setMyIpAddress(String myIpAddress) {
		PacFunctions.myIpAddress = myIpAddress;
	}

	public static String getMyIpAddress() {
		return myIpAddress;
	}

	public static void setHostMap(HashMap<String, String> hostMap) {
		PacFunctions.hostMap = hostMap;
	}
}
