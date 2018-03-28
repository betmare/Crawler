package com.synertrade.crawler.html;

import com.synertrade.crawler.sync.ParallelLinks;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class HtmlProcessor {
    private String url;
    private ForkJoinPool forkJoinPool;
    private Map<String, Long> linksVisited;

    public HtmlProcessor(String url) {
        this.url = url;
        linksVisited = Collections.synchronizedMap(new HashMap<String, Long>());
        forkJoinPool = ForkJoinPool.commonPool();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Long> getLinksVisited() {
        return linksVisited;
    }

    public void setLinksVisited(Map<String, Long> linksVisited) {
        this.linksVisited = linksVisited;
    }

    public void start() {
        forkJoinPool.invoke(new ParallelLinks(url,this));
    }

}
