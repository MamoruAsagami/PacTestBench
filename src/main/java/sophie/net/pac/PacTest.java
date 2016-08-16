/*
 * Copyright(c) 2016 Mamoru Asagami. All rights reserved.
 * 
*/

package sophie.net.pac;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import static sophie.net.pac.TraceablePacFunctions.quote;
import static sophie.net.pac.TraceablePacFunctions.toStringFuncall;

public class PacTest {
	static Charset charset;
	static boolean trace;
	static boolean debug;
	

	public static void setCharset(Charset charset) {
		PacTest.charset = charset;
	}

	public static void setTrace(boolean trace) {
		PacTest.trace = trace;
	}
	
	public static void setDebug(boolean debug) {
		PacTest.debug = debug;
	}
	
	public static void setMyIpAddress(String myIpAddress) {
		System.out.println("myIpAddress: " + quote(myIpAddress));
		PacFunctions.setMyIpAddress((myIpAddress != null &&  myIpAddress.length() != 0)? myIpAddress: null);
	}
	
	public static String getMyIpAddress() {
		return PacFunctions.getMyIpAddress();
	}

	public static void setHosts(String[] nameAddressPairs) {
		System.out.print("hosts: (");
		HashMap<String, String> map = new HashMap<String, String>();
		for(int i = 0; i + 1 < nameAddressPairs.length; i += 2) {
			map.put(nameAddressPairs[i], nameAddressPairs[i+1]);
			System.out.print(quote(nameAddressPairs[i]) + "=" + quote(nameAddressPairs[i+1]));
			if(i + 2 + 1 < nameAddressPairs.length) {
				System.out.print(", ");
			}
		}
		System.out.println(")");
		PacFunctions.setHostMap(map);
	}
	
	public static void setToday(String date, String time, String timeZone) {
		final TimeZone tz;
		if(timeZone != null) {
			tz = TimeZone.getTimeZone(timeZone);
			if(!tz.getID().equalsIgnoreCase(timeZone)) {
				System.err.println("The specified TimeZone: " + timeZone + " was taken as " + tz.getID() + ".");
			}
		} else {
			tz = TimeZone.getDefault();
		}
		Calendar calendar = Calendar.getInstance(tz);
		if(date != null) {
			if(date.matches("\\d\\d\\d\\d+[/-]\\d\\d?[/-]\\d\\d?")) {
				String[] ymd = date.split("[/-]");
				int year = Integer.valueOf(ymd[0]);
				int month = Integer.valueOf(ymd[1]) - 1;
				int day = Integer.valueOf(ymd[2]);
				calendar.set(year, month, day);
			} else if(date.matches("\\d\\d?[/-]\\d\\d?[/-]\\d\\d\\d\\d+")) {
				String[] ymd = date.split("[/-]");
				int year = Integer.valueOf(ymd[2]);
				int month = Integer.valueOf(ymd[1]) - 1;
				int day = Integer.valueOf(ymd[0]);
				calendar.set(year, month, day);
			} else {
				System.err.println("Use yyyy-mm-dd, yyyy/mm/dd, dd-mm-yyyy or dd/mm/yyyy as date format.");
			}
		}
		if(time != null) {
			if(time.matches("\\d\\d?:\\d\\d:\\d\\d")) {
				String[] hms = time.split(":");
				int hour = Integer.valueOf(hms[0]);
				int minute = Integer.valueOf(hms[1]);
				int sec = Integer.valueOf(hms[2]);
				calendar.set(Calendar.HOUR_OF_DAY, hour);
				calendar.set(Calendar.MINUTE , minute);
				calendar.set(Calendar.SECOND, sec);
			} else if(time.matches("\\d\\d?:\\d\\d")) {
				String[] hms = time.split(":");
				int hour = Integer.valueOf(hms[0]);
				int minute = Integer.valueOf(hms[1]);
				calendar.set(Calendar.HOUR_OF_DAY, hour);
				calendar.set(Calendar.MINUTE , minute);
			} else {
				System.err.println("Use hh:mm:ss or hh:mm as time format.");
			}
		}
		PacFunctions.setToday(calendar.getTime());
		DateFormat dateFormat = new SimpleDateFormat("E yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
		String localTime = dateFormat.format(calendar.getTime());
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		String gmtTime = dateFormat.format(calendar.getTime());
		if(timeZone != null && !timeZone.equals("GMT")) {
			dateFormat.setTimeZone(tz);
			String timeZoneTime = dateFormat.format(calendar.getTime());
			System.out.println("LocalTime: " + localTime + "  GMT: " + gmtTime + "  " + timeZone + ": " + timeZoneTime);
		} else {
			System.out.println("LocalTime: " + localTime + "  GMT: " + gmtTime);
		}
	}

	static void loadPlatformScripts(ScriptEngine engine) throws ScriptException, IOException {
		String[] scripts = {"callbackAdapter.js"};
		for(String script: scripts) {
			Reader reader = new BufferedReader(new InputStreamReader(
					PacTest.class.getResourceAsStream(script)));
			try {
				engine.eval(reader);
			} finally {
				reader.close();
			}
		}
	}
	
	static void loadPacScript(String script, ScriptEngine engine) throws ScriptException, IOException {
		Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(script), charset));
		try {
			engine.eval(reader);
		} finally {
			reader.close();
		}
	}
	
	static void runDebugScript(ScriptEngine engine) throws ScriptException, IOException {
		String[] scripts = {"debug.js"};
		for(String script: scripts) {
			Reader reader = new BufferedReader(new InputStreamReader(
					PacTest.class.getResourceAsStream(script)));
			try {
				engine.eval(reader);
			} finally {
				reader.close();
			}
		}
	}
	
	static void runDebugScript() throws ScriptException, IOException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		if(engine != null) {
			engine.eval("var PacFunctions =  Packages.sophie.net.pac.TraceablePacFunctions;");
			loadPlatformScripts(engine);
			runDebugScript(engine);
		} else {
			System.err.println("Can't find JavaScript engine.");
		}
	}
	
	static void optionsUsage() {
		System.out.println("Options");
		System.out.println("    -date yyyy-mm-dd          # date to be used as current time");
		System.out.println("    -time hh:mm[:ss]          # time to be used as current time");
		System.out.println("    -timezone timezone        # timezone of -date and -time specification");
		System.out.println("    -tz timezone              # the same as timezone");
		System.out.println("    -myIpAddress address      # IP address of the host");
		System.out.println("    -hosts (name address ...) # predefined name resolution entries.");
		System.out.println("                              # Use null or 0.0.0.0 as addresses for unresolvable names.");
	}
	
	static void options(String[] args) {
    	try {
	    	boolean showUsage = false;
	    	String date = null;
	    	String time = null;
	    	String timeZone = null;
	    	boolean dateSpecified = false;
	    	String myIpAddress = getMyIpAddress();
	    	boolean myIpAddressSpecified = false;
			ArrayList<String> hosts = new ArrayList<String>();
			boolean hostsSpecified = false;
	    	int i = 0;
			for (; i < args.length && args[i].charAt(0) == '-'; i++) {
				if (args[i].equalsIgnoreCase("-date")) {
					if(i + 1 < args.length) {
						i++;
						date = args[i];
						dateSpecified = true;
					} else {
						System.out.println("date missing after " + args[i] + ".");
						showUsage = true;
						break;
					}
				} else if (args[i].equalsIgnoreCase("-time")) {
					if(i + 1 < args.length) {
						i++;
						time = args[i];
						dateSpecified = true;
					} else {
						System.out.println("time missing after " + args[i] + ".");
						showUsage = true;
						break;
					}
				} else if (args[i].equalsIgnoreCase("-tz") || args[i].equalsIgnoreCase("-timezone")) {
					if(i + 1 < args.length) {
						i++;
						timeZone = args[i];
					} else {
						System.out.println("timezone missing after " + args[i] + ".");
						showUsage = true;
						break;
					}
				} else if (args[i].equalsIgnoreCase("-myIpAddress")) {
					if(i + 1 < args.length) {
						i++;
						myIpAddress = args[i];
						myIpAddressSpecified = true;
					} else {
						System.out.println("myIpAddress missing after " + args[i] + ".");
						showUsage = true;
						break;
					}
				} else if (args[i].equalsIgnoreCase("-hosts")) {
					if(i + 1 < args.length) {
						i++;
						String arg  = args[i];
						if(arg.startsWith("(")) {
							arg = arg.substring(1);
							if(arg.length() > 0) {
								if(!arg.endsWith(")")) {
									hosts.add(arg);
								}
							}
							for(;;) {
								if(arg.endsWith(")")) {
									if(arg.length() > 1) {
										hosts.add(arg.substring(0, arg.length() - 1));
									}
									break;
								}
								if(i + 1 >= args.length) {
									System.out.println("')' expected with " + args[i] + ".");
									showUsage = true;
									break;
								}
								i++;
								arg  = args[i];
								if(!arg.endsWith(")")) {
									hosts.add(arg);
								}
							}
							if(hosts.size() % 2 == 1) {
								System.out.println("(name address ...) mismatch with " + args[i] + ".");
								showUsage = true;
							}
							hostsSpecified = true;
						} else {
							System.out.println("'(' expected after " + args[i] + ".");
							showUsage = true;
							break;
						}
					} else {
						System.out.println("(name address ...) missing after " + args[i] + ".");
						showUsage = true;
						break;
					}
				} else {
					System.out.println("Unknown option: " + args[i] + ".");
					showUsage = true;
					break;
				}
			}
			if(!showUsage) {
	        	if((args.length - i) == 0) {
	        		if(dateSpecified)
	        			setToday(date, time, timeZone);
	        		if(myIpAddressSpecified)
	        			setMyIpAddress(myIpAddress);
	    			if(hostsSpecified)
	    				setHosts(hosts.toArray(new String[hosts.size()]));
	        	} else {
	        		showUsage = true;
	        	}
	        }
        	if(showUsage) {
        		optionsUsage();
        	}
        } catch(Exception e) {
        	e.printStackTrace(System.out);
        }
	}

	static void runFindProxyForURLs(String pacFileName, String urlListFileName) throws ScriptException, IOException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		if(engine != null) {
			engine.eval(trace? "var PacFunctions =  Packages.sophie.net.pac.TraceablePacFunctions;" : "var PacFunctions =  Packages.sophie.net.pac.PacFunctions;");
			loadPlatformScripts(engine);
			loadPacScript(pacFileName, engine);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(urlListFileName), charset));
			try {
				SimpleLineParser parser = new SimpleLineParser();
				parser.setCommentIntroducer("#");
				int passCount = 0;
				int failCount = 0;
				String line;
				for(int n = 1; (line = reader.readLine()) != null; n++) {
		    		System.out.println(n + ": " + line);
					String[] args = parser.parse(line);
					if(args.length > 0) {
						if(args[0].startsWith("-")) {
							options(args);
						} else {
							String url = args[0];
	    					String host = new URL(url).getHost();
	    					Object result = engine.eval("FindProxyForURL('" + url + "', '" + host + "')");
	    					System.out.println(toStringFuncall("FindProxyForURL", url, host) + ": " + quote((result != null)? result.toString(): null));
	    					if(args.length >= 2) {
	    						if(args[1].equals(result)) {
	    							System.out.println("Pass");
	    							passCount++;
	    						} else {
	    							System.out.println("Fail - result: " + quote((result != null)? result.toString(): null) + " expected: " +  quote(args[1]));
	    							failCount++;
	    						}
	    					}
	    					if(args.length > 2) {
	    						System.out.println("Warning: Extra " + (args.length > 3? "tokens": "token") + ".");
	    					}
						}
						System.out.println();
					}
				}
				if(passCount + failCount > 0) {
					System.out.println("Pass: " + passCount + " Fail: " + failCount);
				}
			} finally {
				reader.close();
			}
		} else {
			System.err.println("Can't find JavaScript engine.");
		}
	}
	
	static void usage() {
		System.err.println("Usage: PacTest [options] pac-file url-list-file");
		System.err.println("Options");
		System.err.println("    -trace=false|true         # trace message option");
		System.err.println("    -date yyyy-mm-dd          # date to be used as current time");
		System.err.println("    -time hh:mm[:ss]          # time to be used as current time");
		System.err.println("    -timezone timezone        # timezone of -date and -time specification");
		System.err.println("    -tz timezone              # the same as timezone");
		System.err.println("    -myIpAddress address      # IP address of the host");
		System.err.println("    -hosts (name address ...) # predefined name resolution entries.");
		System.err.println("                              # Use null or 0.0.0.0 as address for unresolvable names.");
		System.err.println("    -encoding charset         # Encoding of pac-file and url-list-file");
	}
	
    public static void main(String[] args) throws Exception {
    	try {
	    	boolean showUsage = false;
	    	Charset charset = Charset.defaultCharset();
	    	String date = null;
	    	String time = null;
	    	String timeZone = null;
	    	boolean trace = true;
	    	String myIpAddress = null;
			ArrayList<String> hosts = new ArrayList<String>();
	    	boolean debug = false;
			int i = 0;
			for (; i < args.length && args[i].charAt(0) == '-'; i++) {
				if (args[i].equalsIgnoreCase("-date")) {
					if(i + 1 < args.length) {
						i++;
						date = args[i];
					} else {
						System.err.println("date missing after " + args[i] + ".");
						showUsage = true;
						break;
					}
				} else if (args[i].equalsIgnoreCase("-time")) {
					if(i + 1 < args.length) {
						i++;
						time = args[i];
					} else {
						System.err.println("time missing after " + args[i] + ".");
						showUsage = true;
						break;
					}
				} else if (args[i].equalsIgnoreCase("-tz") || args[i].equalsIgnoreCase("-timezone")) {
					if(i + 1 < args.length) {
						i++;
						timeZone = args[i];
					} else {
						System.err.println("timezone missing after " + args[i] + ".");
						showUsage = true;
						break;
					}
				} else if (args[i].equalsIgnoreCase("-trace=false") || args[i].equalsIgnoreCase("-trace=no")) {
					trace = false;
				} else if (args[i].equalsIgnoreCase("-trace=true") || args[i].equalsIgnoreCase("-trace=yes")) {
					trace = true;
				} else if (args[i].equalsIgnoreCase("-myIpAddress")) {
					if(i + 1 < args.length) {
						i++;
						myIpAddress = args[i];
					} else {
						System.err.println("myIpAddress missing after " + args[i] + ".");
						showUsage = true;
						break;
					}
				} else if (args[i].equalsIgnoreCase("-hosts")) {
					if(i + 1 < args.length) {
						i++;
						String arg  = args[i];
						if(arg.startsWith("(")) {
							arg = arg.substring(1);
							if(arg.length() > 0) {
								if(!arg.endsWith(")")) {
									hosts.add(arg);
								}
							}
							for(;;) {
								if(arg.endsWith(")")) {
									if(arg.length() > 1) {
										hosts.add(arg.substring(0, arg.length() - 1));
									}
									break;
								}
								if(i + 1 >= args.length) {
									System.err.println("')' expected with " + args[i] + ".");
									showUsage = true;
									break;
								}
								i++;
								arg  = args[i];
								if(!arg.endsWith(")")) {
									hosts.add(arg);
								}
							}
							if(hosts.size() % 2 == 1) {
								System.err.println("(name address ...) mismatch with " + args[i] + ".");
								showUsage = true;
							}
						} else {
							System.err.println("'(' expected after " + args[i] + ".");
							showUsage = true;
							break;
						}
					} else {
						System.err.println("(name address ...) missing after " + args[i] + ".");
						showUsage = true;
						break;
					}
				} else if (args[i].equalsIgnoreCase("-encoding")) {
					if(i + 1 < args.length) {
						i++;
						charset = Charset.forName(args[i]);
					} else {
						System.err.println("charset missing after " + args[i] + ".");
						showUsage = true;
						break;
					}
				} else if (args[i].equalsIgnoreCase("-debug")) {
					debug = true;
				} else {
					System.err.println("Unknown option: " + args[i] + ".");
					showUsage = true;
					break;
				}
			}
			if(!showUsage) {
	        	if((args.length - i) == 2) {
	        		setDebug(debug);
	        		setCharset(charset);
	        		setTrace(trace);
	    			setToday(date, time, timeZone);
	    			if(myIpAddress != null)
	    				setMyIpAddress(myIpAddress);
	    			if(hosts.size() != 0)
	    				setHosts(hosts.toArray(new String[hosts.size()]));
	    			if(debug) {
	        			runDebugScript();
	    			}
	    			runFindProxyForURLs(args[i + 0], args[i + 1]);
	        	} else {
	        		showUsage = true;
	        	}
	        }
        	if(showUsage) {
        		usage();
        	}
        } catch(Exception e) {
        	e.printStackTrace(System.err);
        }
    }
}