# PacTestBench
Proxy.pac is indispensable for you if you have to move around multiple networks or you have to use multiple proxies in your network.
However, it's not easy to debug or test Proxy.pac in standard environments.
PacTestBench is out there for you to ease the debgging and testing of Proxy.pac.
  You can use PacTestBench as follows:
## 1 Preparation
1. Place your proxy.pac in testbed directory
2. Place your test data in  testbed directory as urls.txt
The grammar of  urls.txt is as follows:

  file = (URL , expected? | options)*
  URL: URL to be tested.
  expected (oprional): expected result of  FindProxyForURL(url, host) function.
  options: -date yyyy-mm-dd -time hh:mm[:ss] -timezone timezone -myIpAddress address -hosts (name address ...)

## 2 Running it by Gradle
1. Arrange ext { ... } section of build.gradle
2. Run as in follows:

`gradle run [>report.txt]`

## 3 Running it by Java
**Usage:**
`java -jar build/libs/PacTestBench.jar [options] pac-file url-list-file [>report.txt]`

**Options**

| Syntax | Description |
|--------|--------|
|-trace=false &#124; true    |# trace message option        |
|-date yyyy-mm-dd          |# date to be used as current time|
|-time hh:mm[:ss]          |# time to be used as current time     |
|-timezone timezone        |# timezone of -date and -time specification|
|-tz timezone              |# the same as timezone|
|-myIpAddress address      |# IP address of the host        |
|-hosts (name address ...) |# predefined name resolution entries.  Use null or 0.0.0.0 as address for unresolvable names.|
|-encoding charset         |# Encoding of pac-file and url-list-file, UTF-8, for example|

## 4 Sample report (partial)

```java
24: http://google.com/ "PROXY proxy.visiting.com:9090" # Will pass
     6: weekdayRange("SAT", "SUN"): false
    12: dateRange(24, "SEP", 2018, 28, "SEP", 2018): true
    12: timeRange(8, 15, 18, 0): true
    13: isPlainHostName("google.com"): false
    14: dnsDomainIs("google.com", ".visiting.com"): false
    15: dnsDomainIs("google.com", ".local"): false
    16: shExpMatch("google.com", "127.0.0.1"): false
FindProxyForURL("http://google.com/", "google.com"): "PROXY proxy.visiting.com:9090"
Pass


25: http://google.com/ DIRECT # Will fail
     6: weekdayRange("SAT", "SUN"): false
    12: dateRange(24, "SEP", 2018, 28, "SEP", 2018): true
    12: timeRange(8, 15, 18, 0): true
    13: isPlainHostName("google.com"): false
    14: dnsDomainIs("google.com", ".visiting.com"): false
    15: dnsDomainIs("google.com", ".local"): false
    16: shExpMatch("google.com", "127.0.0.1"): false
FindProxyForURL("http://google.com/", "google.com"): "PROXY proxy.visiting.com:9090"
Fail - result: "PROXY proxy.visiting.com:9090" expected: "DIRECT"

Pass: 1 Fail: 1

```


