package com.synertrade.crawler;

import com.synertrade.crawler.processor.ParallelProcessor;
import com.synertrade.crawler.util.CounterVisits;
import com.synertrade.crawler.util.MapToXml;

import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    private static final String URL_LINK = "https://en.wikipedia.org/wiki/Europe";

    public static boolean isUrl(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }

    }
    public static void main(String args[]) {
        CounterVisits counterVisits = null;
        if(args != null && args.length>0) {
            if(isUrl(args[0])) {
                counterVisits = new CounterVisits(new ParallelProcessor(args[0]).start());
            }
        } else {
            counterVisits = new CounterVisits(new ParallelProcessor(URL_LINK).start());
        }
        if(counterVisits != null) {
            new MapToXml(counterVisits).generateXml();
        }
    }
}
