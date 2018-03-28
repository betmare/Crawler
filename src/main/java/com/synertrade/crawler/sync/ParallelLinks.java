package com.synertrade.crawler.sync;

import com.synertrade.crawler.html.ParallelProcessor;

import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class ParallelLinks extends RecursiveAction {

    private String url;
    private ParallelProcessor parallelProcessor;

    public ParallelLinks(String url, ParallelProcessor parallelProcessor) {
        this.parallelProcessor = parallelProcessor;
        this.url = url;
    }
    @Override
    protected void compute() {
        if(!parallelProcessor.getLinksVisited().containsKey(url)) {
            invokeAll(createSubTasks());
        } else {
            parallelProcessor.getLinksVisited().get(url).add(BigInteger.ONE);
            System.out.println("Crawling url: "+url+" visits "+
                    parallelProcessor.getLinksVisited().get(url));
        }
    }
    private List<ParallelLinks> createSubTasks() {
        List<ParallelLinks> linksToProcess = new ArrayList<>();
        NodeList linksList = getLinks(url);
        if(linksList != null) {
            for(int i = 0; i<linksList.size(); i++) {
                LinkTag link = (LinkTag) linksList.elementAt(i);
                String linkUrl = link.extractLink();
                if (!linkUrl.isEmpty() &&
                        !parallelProcessor.getLinksVisited().containsKey(linkUrl)) {
                    parallelProcessor.getLinksVisited().put(url, BigInteger.ONE);
                    linksToProcess.add(new ParallelLinks(linkUrl, parallelProcessor));
                    System.out.println("Crawling url: " + url + " visits " +
                            parallelProcessor.getLinksVisited().get(linkUrl));
                } else if (!linkUrl.isEmpty()) {
                    BigInteger value = parallelProcessor.getLinksVisited().get(linkUrl).add(BigInteger.ONE);
                    parallelProcessor.getLinksVisited().put(linkUrl, value);
                    System.out.println("Crawling url: " + url + " visits " +
                            parallelProcessor.getLinksVisited().get(linkUrl));

                }
            }
        }
        return linksToProcess;
    }

    private NodeList getLinks(String url){
        try {
            URL linkUrl = new URL(url);
            Parser parser = new Parser(linkUrl.openConnection());
            return parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
        } catch (Exception e) {
            System.out.println("Error get Links");
        }
        return null;
    }
}
