package com.synertrade.crawler.processor;

import com.synertrade.crawler.sync.ParallelLinks;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class ParallelProcessor {
    private String url;
    private ForkJoinPool forkJoinPool;
    private Map<String, BigInteger> linksVisited;
    private final int MAX_RETRIES_DEFAULT = 32;


    public ParallelProcessor(String url) {
        this.url = url;
        linksVisited = Collections.synchronizedMap(new HashMap<String, BigInteger>());
        forkJoinPool = new ForkJoinPool(MAX_RETRIES_DEFAULT);
    }

    public Map<String, BigInteger> getLinksVisited() {
        return linksVisited;
    }

    public Map<String, BigInteger> start() {
        forkJoinPool.invoke(new ParallelLinks(url,this));
        return getLinksVisited();
    }
}
