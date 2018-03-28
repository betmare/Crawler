package com.synertrade.crawler.util;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.*;

@XmlRootElement
public class CounterVisits {
    @XmlElementWrapper
    private Map<String, BigInteger> urls = new HashMap<>();

    public CounterVisits(){}

    public CounterVisits(Map<String, BigInteger> urls) {
        this.urls = urls;
    }

    public Map<String, BigInteger> getCounterVisits() {
        return urls;
    }

    public void setUrls(Map<String, BigInteger> urls) {
        this.urls = urls;
    }
}
