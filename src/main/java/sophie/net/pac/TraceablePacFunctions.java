/*
 * Copyright(c) 2016 Mamoru Asagami. All rights reserved.
 * 
*/

package sophie.net.pac;

import java.util.regex.Pattern;

public class TraceablePacFunctions {
	static final Pattern numberPattern = Pattern.compile("[+-]?\\d+");
	static int line;
	
	public static String quote(String s) {
		if(s == null) {
			return "null";
		} else if(numberPattern.matcher(s).matches()) {
			return s;
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append('"');
			for(char c: s.toCharArray()) {
				if(c == '\\' || c == '"') {
					sb.append('\\');
				}
				sb.append(c);
			}
			sb.append('"');
			return sb.toString();
		}
	}
	
	private static String lineIndex() {
		return line != 0 ? String.format("%5d: ", line): "   - ";
	}
	
	public static String toStringFuncall(String name, String ... args) {
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		sb.append('(');
		if(args.length > 0) {
			sb.append(quote(args[0]));
			for(int i = 1; i < args.length; i++) {
				sb.append(", ");
				sb.append(quote(args[i]));
			}
		}
		sb.append(')');
		return sb.toString();
	}
	
	private static final Pattern Java7FramePat1 = Pattern.compile(".*:\\d+ \\(.+\\)"); // at <Unknown source>:5 (FindProxyForURL)
	private static final Pattern Java8FramePat2 = Pattern.compile(".*:\\d+\\)"); // at FindProxyForURL (<eval>:12)
	
	public static void captureLine(String stack) {
		int n = 0;
		String[] frames = stack.split("\\r\\n|\\r|\\n");
		boolean showStackFrame = false;
		if(showStackFrame) { // Intentional dead code
			for(String frame: frames) {
				System.out.println(frame);
			}
		}
		if(frames.length >= 3) {
			// frame = (java8 js frame)? frames[3]: frames[2]
			final String frame = ((frames[0].startsWith("TypeError:") // TypeError: null has no such function "toString"
					&& frames.length >= 4)?
		       frames[3]: frames[2]
			).trim();
			try {
				if(Java7FramePat1.matcher(frame).matches()) {
					int begin = frame.lastIndexOf(':') + 1;
					int end = frame.lastIndexOf('(');
					n = Integer.valueOf(frame.substring(begin, end).trim());
				} else if(Java8FramePat2.matcher(frame).matches()) {
					int begin = frame.lastIndexOf(':') + 1;
					int end = frame.lastIndexOf(')');
					n = Integer.valueOf(frame.substring(begin, end).trim());
				} else {
					// Nothing to do
				}
			} catch(Exception e) {
				// Wrong line format.  Let's just ignore it.
			}
		}
		line = n;
	}
	
	public static void alert(String message) {
		System.out.println(lineIndex() + toStringFuncall("alert", message));
		PacFunctions.alert(message);
	}

	public static boolean dateRange(String arg1) {
		boolean dateRange = PacFunctions.dateRange(arg1);
		System.out.println(lineIndex() + toStringFuncall("dateRange", arg1) + ": " + dateRange);
		return dateRange;
	}

	public static boolean dateRange(String arg1, String arg2) {
		boolean dateRange = PacFunctions.dateRange(arg1, arg2);
		System.out.println(lineIndex() + toStringFuncall("dateRange", arg1,  arg2) + ": " + dateRange);
		return dateRange;
	}

	public static boolean dateRange(String arg1, String arg2, String arg3) {
		boolean dateRange = PacFunctions.dateRange(arg1, arg2, arg3);
		System.out.println(lineIndex() + toStringFuncall("dateRange", arg1,  arg2, arg3) + ": " + dateRange);
		return dateRange;
	}

	public static boolean dateRange(String arg1, String arg2, String arg3, String arg4) {
		boolean dateRange = PacFunctions.dateRange(arg1, arg2, arg3, arg4);
		System.out.println(lineIndex() + toStringFuncall("dateRange", arg1,  arg2, arg3, arg4) + ": " + dateRange);
		return dateRange;
	}

	public static boolean dateRange(String arg1, String arg2, String arg3, String arg4, String arg5) {
		boolean dateRange = PacFunctions.dateRange(arg1, arg2, arg3, arg4, arg5);
		System.out.println(lineIndex() + toStringFuncall("dateRange", arg1,  arg2, arg3, arg4, arg5) + ": " + dateRange);
		return dateRange;
	}

	public static boolean dateRange(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
		boolean dateRange = PacFunctions.dateRange(arg1, arg2, arg3, arg4, arg5, arg6);
		System.out.println(lineIndex() + toStringFuncall("dateRange", arg1,  arg2, arg3, arg4, arg5, arg6) + ": " + dateRange);
		return dateRange;
	}

	public static boolean dateRange(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7) {
		boolean dateRange = PacFunctions.dateRange(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
		System.out.println(lineIndex() + toStringFuncall("dateRange", arg1,  arg2, arg3, arg4, arg5, arg6, arg7) + ": " + dateRange);
		return dateRange;
	}

	public static boolean dnsDomainIs(String host, String domain) {
		boolean dnsDomainIs = PacFunctions.dnsDomainIs(host, domain);
		System.out.println(lineIndex() + toStringFuncall("dnsDomainIs", host, domain) + ": " + dnsDomainIs);
		return dnsDomainIs;
	}

	public static int dnsDomainLevels(String host) {
		int dnsDomainLevels = PacFunctions.dnsDomainLevels(host);
		System.out.println(lineIndex() + toStringFuncall("dnsDomainLevels", host) + ": " + dnsDomainLevels);
		return dnsDomainLevels;
	}	
	
	public static String dnsResolve(String host) {
		String dnsResolve = PacFunctions.dnsResolve(host);
		System.out.println(lineIndex() + toStringFuncall("dnsResolve", host) + ": " + dnsResolve);
		return dnsResolve;
	}

	public static boolean isInNet(String host, String pattern, String mask) {
		boolean isInNet = PacFunctions.isInNet(host, pattern, mask);
		System.out.println(lineIndex() + toStringFuncall("isInNet", host, pattern, mask) + ": " + isInNet);
		return isInNet;
	}
	
	public static boolean isPlainHostName(String host) {
		boolean isPlainHostName = PacFunctions.isPlainHostName(host);
		System.out.println(lineIndex() + toStringFuncall("isPlainHostName", host) + ": " + isPlainHostName);
		return isPlainHostName;
	}

	public static boolean isResolvable(String host) {
		boolean isPlainHostName = PacFunctions.isResolvable(host);
		System.out.println(lineIndex() + toStringFuncall("isResolvable", host) + ": " + isPlainHostName);
		return isPlainHostName;
	}
	
	public static boolean localHostOrDomainIs(String host, String hostdom) {
		boolean localHostOrDomainIs = PacFunctions.localHostOrDomainIs(host, hostdom);
		System.out.println(lineIndex() + toStringFuncall("localHostOrDomainIs", host, hostdom) + ": " + localHostOrDomainIs);
		return localHostOrDomainIs;
	}
	
	
	public static String myIpAddress() {
		String myIpAddress = PacFunctions.myIpAddress();
		System.out.println(lineIndex() + toStringFuncall("myIpAddress") + ": " + quote(myIpAddress));
		return myIpAddress;
	}
	public static boolean shExpMatch(String str, String pat) {
		boolean shExpMatch = PacFunctions.shExpMatch(str, pat);
		System.out.println(lineIndex() + toStringFuncall("shExpMatch", str, pat) + ": " + shExpMatch);
		return shExpMatch;
	}
	
	public static boolean timeRange(String arg1) {
		boolean timeRange = PacFunctions.timeRange(arg1);
		System.out.println(lineIndex() + toStringFuncall("timeRange", arg1) + ": " + timeRange);
		return timeRange;
	}

	public static boolean timeRange(String arg1, String arg2) {
		boolean timeRange = PacFunctions.timeRange(arg1, arg2);
		System.out.println(lineIndex() + toStringFuncall("timeRange", arg1, arg2) + ": " + timeRange);
		return timeRange;
	}

	public static boolean timeRange(String arg1, String arg2, String arg3) {
		boolean timeRange = PacFunctions.timeRange(arg1, arg2, arg3);
		System.out.println(lineIndex() + toStringFuncall("timeRange", arg1, arg2, arg3) + ": " + timeRange);
		return timeRange;
	}

	public static boolean timeRange(String arg1, String arg2, String arg3, String arg4) {
		boolean timeRange = PacFunctions.timeRange(arg1, arg2, arg3, arg4);
		System.out.println(lineIndex() + toStringFuncall("timeRange", arg1, arg2, arg3, arg4) + ": " + timeRange);
		return timeRange;
	}

	public static boolean timeRange(String arg1, String arg2, String arg3, String arg4, String arg5) {
		boolean timeRange = PacFunctions.timeRange(arg1, arg2, arg3, arg4, arg5);
		System.out.println(lineIndex() + toStringFuncall("timeRange", arg1, arg2, arg3, arg4, arg5) + ": " + timeRange);
		return timeRange;
	}

	public static boolean timeRange(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
		boolean timeRange = PacFunctions.timeRange(arg1, arg2, arg3, arg4, arg5, arg6);
		System.out.println(lineIndex() + toStringFuncall("timeRange", arg1, arg2, arg3, arg4, arg5, arg6) + ": " + timeRange);
		return timeRange;
	}

	public static boolean timeRange(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7) {
		boolean timeRange = PacFunctions.timeRange(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
		System.out.println(lineIndex() + toStringFuncall("timeRange", arg1, arg2, arg3, arg4, arg5, arg6, arg7) + ": " + timeRange);
		return timeRange;
	}

	public static boolean weekdayRange(String arg1) {
		boolean weekdayRange = PacFunctions.weekdayRange(arg1);
		System.out.println(lineIndex() + toStringFuncall("weekdayRange", arg1) + ": " + weekdayRange);
		return weekdayRange;
	}

	public static boolean weekdayRange(String arg1, String arg2) {
		boolean weekdayRange = PacFunctions.weekdayRange(arg1, arg2);
		System.out.println(lineIndex() + toStringFuncall("weekdayRange", arg1, arg2) + ": " + weekdayRange);
		return weekdayRange;
	}

	public static boolean weekdayRange(String arg1, String arg2, String arg3) {
		boolean weekdayRange = PacFunctions.weekdayRange(arg1, arg2, arg3);
		System.out.println(lineIndex() + toStringFuncall("weekdayRange", arg1, arg2, arg3) + ": " + weekdayRange);
		return weekdayRange;
	}
}
