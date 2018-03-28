package com.synertrade.crawler.util;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.*;

@XmlRootElement
public class CounterVisits {
    private Map<String, BigInteger> counterVisits = new HashMap<>();

    public CounterVisits(Map<String, BigInteger> counterVisits) {
        this.counterVisits = counterVisits;
    }

    public Map<String, BigInteger> getCounterVisits() {
        return counterVisits;
    }

    public void setCounterVisits(Map<String, BigInteger> counterVisits) {
        this.counterVisits = counterVisits;
    }
}
