/*
 * Copyright(c) 2016 Mamoru Asagami. All rights reserved.
 * 
*/

//alert("Hello,this is an alert message.")

/*
dateRange(15)
dateRange("JUN")
dateRange(2018)
dateRange(15, "GMT")
dateRange("Aug", "GMT")
dateRange(2016, "GMT")
dateRange(12, 24)
dateRange(24, "DEC")
dateRange("APR", "JUL")
dateRange("MAR", 2020)
dateRange(2015, 2030)
dateRange(20, 31, "GMT")
dateRange(5, "SEP", "GMT")
dateRange("OCT", "DEC", "GMT")
dateRange("MAY", 2525, "GMT")
dateRange(2000, 2500, "GMT")
dateRange(1, "Jan", 2525)
dateRange(1, "Jan", 2525, "GMT")
dateRange(1, "APR", 31, "OCT")
dateRange("NOV", 2020, "FEB", 2021)
dateRange(1, "APR", 31, "OCT", "GMT")
dateRange("NOV", 2020, "FEB", 2021, "GMT")
dateRange(1, "Jan", 2525, 3, "JUN", 2525)
dateRange(1, "Jan", 2525, 3, "JUN", 2550, "GMT")
timeRange(12)
timeRange(16, "GMT")
timeRange(8, 15)
timeRange(8, 15, "GMT")
timeRange(8, 15, 5, 45)
timeRange(8, 15, 5, 45, "GMT")
timeRange(6, 15, 30, 20, 58, 59)
timeRange(6, 15, 30, 20, 58, 59, "GMT")
weekdayRange("MON")
weekdayRange("TUE", "GMT")
weekdayRange("WED", "FRI")
weekdayRange("FRI", "WED", "GMT")

dateRange(4)
dateRange(1, "GMT")
dateRange(1, 15);
dateRange(24, "DEC");
dateRange("JAN", "MAR");
dateRange(1, "JUN", 15, "AUG");
dateRange(1, "JUN", 1995, 15, "AUG", 1995);
dateRange("OCT", 1995, "MAR", 1996);
dateRange(1995);
dateRange(1995, 1997);

timeRange(12);
timeRange(12, 13);
timeRange(12, "GMT");

weekdayRange("MON", "FRI");
weekdayRange("MON", "FRI", "GMT");
weekdayRange("SAT");
weekdayRange("SAT", "GMT");
weekdayRange("FRI", "MON");
*/

/*
weekdayRange("SUN");
weekdayRange("MON");
weekdayRange("TUE");
weekdayRange("WED");
weekdayRange("THU");
weekdayRange("FRI");
weekdayRange("SAT");
weekdayRange("SUN", "GMT");
weekdayRange("MON", "GMT");
weekdayRange("TUE", "GMT");
weekdayRange("WED", "GMT");
weekdayRange("THU", "GMT");
weekdayRange("FRI", "GMT");
weekdayRange("SAT", "GMT");
weekdayRange("WED", "MON");
weekdayRange("WED", "MON", "GMT");
weekdayRange("WED", "TUE");
*/

// The test data below is good for date 15-2-2016 -tz EST -time 12:34:08

/*
// dateRange(day)
dateRange(15)
dateRange(1)
dateRange(2)
dateRange(3)

// dateRange(month)
dateRange("JUN")
dateRange("FEB")
dateRange("MAR")

// dateRange(year)
dateRange(2015)
dateRange(2016)
dateRange(2017)
*/

/*
dateRange(day, GMT)
dateRange(1, "GMT")
dateRange(2, "GMT")
dateRange(3, "GMT")

// dateRange(month, GMT)
dateRange("JAN", "GMT")
dateRange("FEB", "GMT")
dateRange("MAR", "GMT")

// dateRange(year, GMT)
dateRange(2015, "GMT")
dateRange(2016, "GMT")
dateRange(2017, "GMT")

// dateRange(day, day)
dateRange(12, 24)
dateRange(12, 15)
dateRange(12, 16)
dateRange(16, 17)

// dateRange(day, month)
dateRange(15, "FEB")
dateRange(16, "FEB")
dateRange(17, "FEB")
dateRange(15, "MAR")
dateRange(16, "MAR")
dateRange(17, "MAR")

// dateRange(month, month)
dateRange("JAN", "FEB")
dateRange("DEC", "FEB")
dateRange("DEC", "NOV")
dateRange("JAN", "JAN")
dateRange("FEB", "MAR")

// dateRange(month, year)
dateRange("JAN", 2016)
dateRange("FEB", 2016)
dateRange("MAR", 2016)
dateRange("FEB", 2015)
dateRange("FEB", 2016)
dateRange("FEB", 2017)

// dateRange(year, year)
dateRange(2014, 2015)
dateRange(2014, 2016)
dateRange(2016, 2016)
dateRange(2016, 2017)
dateRange(2017, 2017)
*/

/*
// dateRange(day, day, GMT)
dateRange(1, 20, "GMT")
dateRange(1, 14, "GMT")
dateRange(1, 15, "GMT")
dateRange(14, 18, "GMT")
dateRange(15, 18, "GMT")
dateRange(16, 18, "GMT")

// dateRange(day, month, GMT)
dateRange(14, "FEB", "GMT")
dateRange(15, "FEB", "GMT")
dateRange(16, "FEB", "GMT")
dateRange(15, "JAN", "GMT")
dateRange(15, "FEB", "GMT")
dateRange(15, "MAR", "GMT")

// dateRange(month, month, GMT)
dateRange("JAN", "JAN", "GMT")
dateRange("JAN", "FEB", "GMT")
dateRange("JAN", "MAR", "GMT")
dateRange("JAN", "APR", "GMT")
dateRange("FEB", "APR", "GMT")
dateRange("MAR", "APR", "GMT")

// dateRange(month, year, GMT)
dateRange("JAN", 2016, "GMT")
dateRange("FEB", 2016, "GMT")
dateRange("MAR", 2016, "GMT")
dateRange("FEB", 2015, "GMT")
dateRange("FEB", 2016, "GMT")
dateRange("FEB", 2017, "GMT")

// dateRange(year, year, GMT)
dateRange(2014, 2015, "GMT")
dateRange(2014, 2016, "GMT")
dateRange(2016, 2016, "GMT")
dateRange(2016, 2017, "GMT")
dateRange(2017, 2017, "GMT")

// dateRange(day, month, year)
dateRange(15, "FEB", 2016)
dateRange(16, "FEB", 2016)
dateRange(17, "FEB", 2016)
*/

/*
// dateRange(day, month, year, GMT)
dateRange(14, "FEB", 2016, "GMT")
dateRange(15, "FEB", 2016, "GMT")
dateRange(16, "FEB", 2016, "GMT")
dateRange(17, "FEB", 2016, "GMT")
dateRange(14, "FEB", 2015, "GMT")
dateRange(15, "FEB", 2015, "GMT")
dateRange(16, "FEB", 2015, "GMT")
dateRange(17, "FEB", 2015, "GMT")

// dateRange(day, month, day, month)
dateRange(14, "FEB", 15, "FEB")
dateRange(14, "FEB", 16, "FEB")
dateRange(14, "FEB", 20, "FEB")
dateRange(15, "FEB", 20, "FEB")
dateRange(16, "FEB", 20, "FEB")
dateRange(17, "FEB", 20, "FEB")
dateRange(14, "FEB", 17, "FEB")
dateRange(15, "FEB", 17, "FEB")
dateRange(16, "FEB", 17, "FEB")
dateRange(17, "FEB", 17, "FEB")
dateRange(14, "FEB", 15, "FEB")
dateRange(14, "FEB", 16, "FEB")
dateRange(14, "FEB", 17, "FEB")
dateRange(14, "FEB", 18, "FEB")

// dateRange(month, year, month, year)
dateRange("JAN", 2016, "JAN", 2016)
dateRange("JAN", 2016, "FEB", 2016)
dateRange("JAN", 2016, "MAR", 2016)
dateRange("JAN", 2016, "MAY", 2016)
dateRange("FEB", 2016, "MAY", 2016)
dateRange("MAR", 2016, "MAY", 2016)
*/

/*
// dateRange(day, month, day, month, GMT)
dateRange(14, "FEB", 15, "FEB", "GMT")
dateRange(14, "FEB", 16, "FEB", "GMT")
dateRange(14, "FEB", 20, "FEB", "GMT")
dateRange(15, "FEB", 20, "FEB", "GMT")
dateRange(16, "FEB", 20, "FEB", "GMT")
dateRange(17, "FEB", 20, "FEB", "GMT")
dateRange(14, "FEB", 17, "FEB", "GMT")
dateRange(15, "FEB", 17, "FEB", "GMT")
dateRange(16, "FEB", 17, "FEB", "GMT")
dateRange(17, "FEB", 17, "FEB", "GMT")
dateRange(14, "FEB", 14, "FEB", "GMT")
dateRange(14, "FEB", 15, "FEB", "GMT")
dateRange(14, "FEB", 16, "FEB", "GMT")
dateRange(14, "FEB", 17, "FEB", "GMT")
dateRange(14, "FEB", 18, "FEB", "GMT")

// dateRange(month, year, month, year, GMT)
dateRange("JAN", 2016, "JAN", 2016, "GMT")
dateRange("JAN", 2016, "FEB", 2016, "GMT")
dateRange("JAN", 2016, "MAR", 2016, "GMT")
dateRange("JAN", 2016, "MAY", 2016, "GMT")
dateRange("FEB", 2016, "MAY", 2016, "GMT")
dateRange("MAR", 2016, "MAY", 2016, "GMT")
*/

/*
// dateRange(day, month, year, day, month, year)
dateRange(10, "FEB", 2016, 14, "FEB", 2016)
dateRange(10, "FEB", 2016, 15, "FEB", 2016)
dateRange(10, "FEB", 2016, 16, "FEB", 2016)
dateRange(15, "FEB", 2016, 20, "FEB", 2016)
dateRange(16, "FEB", 2016, 20, "FEB", 2016)
dateRange(17, "FEB", 2016, 20, "FEB", 2016)
dateRange(10, "FEB", 2016, 14, "FEB", 2017)
dateRange(10, "FEB", 2016, 15, "FEB", 2017)
dateRange(10, "FEB", 2016, 16, "FEB", 2017)
*/

/*
// dateRange(day, month, year, day, month, year, GMT)
dateRange(10, "FEB", 2016, 14, "FEB", 2016, "GMT")
dateRange(10, "FEB", 2016, 15, "FEB", 2016, "GMT")
dateRange(10, "FEB", 2016, 16, "FEB", 2016, "GMT")
dateRange(15, "FEB", 2016, 20, "FEB", 2016, "GMT")
dateRange(16, "FEB", 2016, 20, "FEB", 2016, "GMT")
dateRange(17, "FEB", 2016, 20, "FEB", 2016, "GMT")
dateRange(10, "FEB", 2016, 14, "FEB", 2017, "GMT")
dateRange(10, "FEB", 2016, 15, "FEB", 2017, "GMT")
dateRange(10, "FEB", 2016, 16, "FEB", 2017, "GMT")
*/

/*
// timeRange(hour)
timeRange(0)
timeRange(1)
timeRange(2)
timeRange(3)
*/

/*
// timeRange(hour, GMT)
timeRange(16, "GMT")
timeRange(17, "GMT")
timeRange(18, "GMT")

// timeRange(hour, hour)
timeRange(0, 1)
timeRange(0, 2)
timeRange(0, 3)
timeRange(0, 4)
timeRange(0, 4)
timeRange(1, 4)
timeRange(2, 4)
timeRange(3, 4)
*/

/*
// timeRange(hour, hour, GMT)
timeRange(0, 15, "GMT")
timeRange(0, 16, "GMT")
timeRange(0, 17, "GMT")
timeRange(0, 18, "GMT")
timeRange(15, 20, "GMT")
timeRange(16, 20, "GMT")
timeRange(17, 20, "GMT")
timeRange(18, 20, "GMT")
*/

/*
// timeRange(hour, min, hour, min)
timeRange(2, 32, 2, 33)
timeRange(2, 32, 2, 34)
timeRange(2, 32, 2, 35)
timeRange(2, 32, 2, 36)
timeRange(2, 33, 2, 36)
timeRange(2, 34, 2, 36)
timeRange(2, 35, 2, 36)
timeRange(2, 36, 2, 36)
timeRange(2, 0, 1, 59)
*/

/*
// timeRange(hour, min, hour, min, GMT)
timeRange(2, 32, 17, 33, "GMT")
timeRange(2, 32, 17, 34, "GMT")
timeRange(2, 32, 17, 35, "GMT")
timeRange(2, 32, 17, 36, "GMT")
timeRange(17, 33, 18, 0, "GMT")
timeRange(17, 34, 18, 0, "GMT")
timeRange(17, 35, 18, 0, "GMT")
timeRange(17, 36, 18, 0, "GMT")
*/

/*
// timeRange(hour, min, sec, hour, min, sec)
timeRange(2, 34, 0, 2, 34, 6)
timeRange(2, 34, 0, 2, 34, 7)
timeRange(2, 34, 0, 2, 34, 8)
timeRange(2, 34, 0, 2, 34, 9)
timeRange(2, 34, 6, 2, 34, 10)
timeRange(2, 34, 7, 2, 34, 10)
timeRange(2, 34, 8, 2, 34, 10)
timeRange(2, 34, 9, 2, 34, 10)
*/

/*
// timeRange(hour, min, sec, hour, min, sec, GMT)
timeRange(17, 34, 0, 17, 34, 6, "GMT")
timeRange(17, 34, 0, 17, 34, 7, "GMT")
timeRange(17, 34, 0, 17, 34, 8, "GMT")
timeRange(17, 34, 0, 17, 34, 9, "GMT")
timeRange(17, 34, 6, 17, 34, 10, "GMT")
timeRange(17, 34, 7, 17, 34, 10, "GMT")
timeRange(17, 34, 8, 17, 34, 10, "GMT")
timeRange(17, 34, 9, 17, 34, 10, "GMT")
*/

/*
// weekdayRange(weekday)
weekdayRange("SUN");
weekdayRange("MON");
weekdayRange("TUE");
weekdayRange("WED");
weekdayRange("THU");
weekdayRange("FRI");
weekdayRange("SAT");
*/

/*
// weekdayRange(weekday, GMT)
weekdayRange("SUN", "GMT");
weekdayRange("MON", "GMT");
weekdayRange("TUE", "GMT");
weekdayRange("WED", "GMT");
weekdayRange("THU", "GMT");
weekdayRange("FRI", "GMT");
weekdayRange("SAT", "GMT");

// weekdayRange(weekday, weekday)
weekdayRange("SAT", "MON")
weekdayRange("SAT", "TUE")
weekdayRange("SAT", "WED")
weekdayRange("MON", "MON")
weekdayRange("MON", "TUE")
weekdayRange("MON", "WED")
weekdayRange("MON", "THU")
weekdayRange("TUE", "THU")
weekdayRange("WED", "THU")
weekdayRange("THU", "THU")
*/

/*
// weekdayRange(weekday, weekday, GMT)
weekdayRange("SAT", "SUN", "GMT")
weekdayRange("SAT", "MON", "GMT")
weekdayRange("SAT", "TUE", "GMT")
weekdayRange("SAT", "WED", "GMT")
weekdayRange("SUN", "SUN", "GMT")
weekdayRange("SUN", "MON", "GMT")
weekdayRange("SUN", "TUE", "GMT")
weekdayRange("SUN", "WED", "GMT")
weekdayRange("SUN", "THU", "GMT")
weekdayRange("MON", "THU", "GMT")
weekdayRange("TUE", "THU", "GMT")
weekdayRange("WED", "THU", "GMT")
weekdayRange("THU", "THU", "GMT")
*/

/*
dnsDomainIs("www.mozilla.org", ".mozilla.org")
dnsDomainIs("www", ".mozilla.org")
dnsDomainIs("www.mozilla.org", "www.mozilla.org")
dnsDomainIs("www.mozilla.org", "mozilla.org")
dnsDomainIs("www.example.com", ".example.com")
dnsDomainIs("www.mcom.com", ".example.com")
*/

/*
dnsResolve("google.com")
dnsResolve("google.xyz")
*/

/*
dnsDomainLevels("www.mozilla.org")
dnsDomainLevels("www")
*/

/*
isInNet("198.95.249.79", "198.95.249.79", "255.255.255.255")
isInNet("198.95.249.80", "198.95.249.79", "255.255.255.255")
isInNet("198.95.249.80", "198.95.249.79", "255.255.255.0")
isInNet("198.95.255.255", "198.95.0.0", "255.255.0.0")
isInNet("google.com", "216.58.0.0", "255.255.0.0")
*/

/*
isPlainHostName("redwood")
isPlainHostName("google.com")
*/

/*
isResolvable("google.com")
isResolvable("google.xyz")
*/

/*
localHostOrDomainIs("www.example.com", "www.example.com")
localHostOrDomainIs("www", "www.example.com")
localHostOrDomainIs("www", "home.example.com")
localHostOrDomainIs("www.mcom.com", "www.example.com")
localHostOrDomainIs("home.example.com", "www.example.com")
*/

/*
myIpAddress()
*/


// Don't enclose the following lines by /* ... */ because they have */ in it.
// shExpMatch("http://home.netscape.com/people/ari/index.html", "*/ari/*");
// shExpMatch("http://home.netscape.com/people/montulli/index.html", "*/ari/*");
