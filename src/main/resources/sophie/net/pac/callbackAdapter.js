/*
 * Copyright(c) 2016 Mamoru Asagami. All rights reserved.
 * 
*/

function captureLine() {  
  try {
    null.toString(); // raise an exception
  } catch (e) {
    PacFunctions.captureLine(e.stack)
  }
}

function alert(message) {
   captureLine()
   PacFunctions.alert(message);
}

function dateRange(arg1, arg2, arg3, arg4, arg5, arg6, arg7) {
  captureLine()
  switch(arguments.length) {
  case 1: return PacFunctions.dateRange(arg1);
  case 2: return PacFunctions.dateRange(arg1, arg2);
  case 3: return PacFunctions.dateRange(arg1, arg2, arg3);
  case 4: return PacFunctions.dateRange(arg1, arg2, arg3, arg4);
  case 5: return PacFunctions.dateRange(arg1, arg2, arg3, arg4, arg5);
  case 6: return PacFunctions.dateRange(arg1, arg2, arg3, arg4, arg5, arg6);
  case 7: return PacFunctions.dateRange(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
  default: throw new Packages.java.lang.IllegalArgumentException("dateRange()");
  }
}

function dnsDomainIs (host, domain) {
  captureLine()
  return PacFunctions.dnsDomainIs (host, domain);
}

function dnsDomainLevels (host) {
  captureLine()
  return PacFunctions.dnsDomainLevels (host);
}

function dnsResolve (host) {
  captureLine()
  return PacFunctions.dnsResolve (host);
}

function isInNet (host, pattern, mask) {
  captureLine()
  return PacFunctions.isInNet (host, pattern, mask);
}

function isPlainHostName (host) {
  captureLine()
  return PacFunctions.isPlainHostName (host);
}

function isResolvable (host) {
  captureLine()
  return PacFunctions.isResolvable (host)
}

function localHostOrDomainIs (host, hostdom) {
  captureLine()
  return PacFunctions.localHostOrDomainIs (host, hostdom);
}

function myIpAddress () {
  captureLine()
  return PacFunctions.myIpAddress();
}

function shExpMatch (str, shexp) {
  captureLine()
  return PacFunctions.shExpMatch (str, shexp);
}

function timeRange(arg1, arg2, arg3, arg4, arg5, arg6, arg7) {
  captureLine()
  switch(arguments.length) {
  case 1: return PacFunctions.timeRange(arg1);
  case 2: return PacFunctions.timeRange(arg1, arg2);
  case 3: return PacFunctions.timeRange(arg1, arg2, arg3);
  case 4: return PacFunctions.timeRange(arg1, arg2, arg3, arg4);
  case 5: return PacFunctions.timeRange(arg1, arg2, arg3, arg4, arg5);
  case 6: return PacFunctions.timeRange(arg1, arg2, arg3, arg4, arg5, arg6);
  case 7: return PacFunctions.timeRange(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
  default: throw new Packages.java.lang.IllegalArgumentException("timeRange()");
  }
}

function weekdayRange(arg1, arg2, arg3) {
  captureLine()
  switch(arguments.length) {
  case 1: return PacFunctions.weekdayRange(arg1);
  case 2: return PacFunctions.weekdayRange(arg1, arg2);
  case 3: return PacFunctions.weekdayRange(arg1, arg2, arg3);
  default: throw new Packages.java.lang.IllegalArgumentException("weekdayRange()");
  }
}

