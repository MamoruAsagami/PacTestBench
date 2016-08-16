function FindProxyForURL(url, host) {
    //alert("url: " + url + " host: " + host)  // You can use alert() but note that only some browsers support it.
    //println("url: " + host)  // You can use println() during testing but you have to remove it when you deploy the script.
    
    // Weekend?
    if(weekdayRange("SAT", "SUN")) {
        // Home network
        return "DIRECT"
    }
    
    // Scheduled Business trip 
    if(dateRange(24, "SEP", 2018,  28, "SEP",2018) && timeRange(8, 15, 18, 00)) {
        if(isPlainHostName(host) ||
           dnsDomainIs(host, ".visiting.com") ||
           dnsDomainIs(host, ".local") ||
           shExpMatch(host, "127.0.0.1")) {
            return "DIRECT"
        }
        return "PROXY proxy.visiting.com:9090";
    }
    
    // Not in office, that is, on the road?
    if(!shExpMatch(myIpAddress(), "125.20.*")) {
        return "DIRECT"
    }
    
    // for Edi
    if (dnsDomainIs(host, ".edi.acme.com") ||
        dnsDomainIs(host, ".ediext.acme.com")) {
      return  "PROXY proxy.edi.acme.com:8080";
    }
    
    // Intranet server?
    if(isPlainHostName(host) ||
       dnsDomainIs(host, ".acme.com") ||
       dnsDomainIs(host, ".local") ||
       shExpMatch(host, "127.0.0.1")) {
        return "DIRECT"
    }
    
    // for https
    if (shExpMatch(url, "https://*") ||
        shExpMatch(url, "HTTPS://*")) {
        return "PROXY proxy.https.acme.com:8080";
    }
    
    // catch all
    return "PROXY proxy.acme.com:8080";
}
