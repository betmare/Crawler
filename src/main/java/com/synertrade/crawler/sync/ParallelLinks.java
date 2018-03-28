package com.synertrade.crawler.sync;

import com.synertrade.crawler.processor.ParallelProcessor;

import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParallelLinks extends RecursiveAction {

    private String url;
    private ParallelProcessor parallelProcessor;
    private final int MAX_URLS = 5000;
    private final static Logger LOGGER = Logger.getLogger(ParallelLinks.class.getName());

    public ParallelLinks(String url, ParallelProcessor parallelProcessor) {
        this.parallelProcessor = parallelProcessor;
        this.url = url;
        LOGGER.setLevel(Level.INFO);
    }
    @Override
    protected void compute() {
        invokeAll(createSubTasks());
    }
    private List<ParallelLinks> createSubTasks() {
        List<ParallelLinks> linksToProcess = new ArrayList<>();
        NodeList linksList = getLinks(url);
        if(linksList != null) {
            for(int i = 0; i<linksList.size(); i++) {
                LinkTag link = (LinkTag) linksList.elementAt(i);
                String linkUrl = link.extractLink();
                if (!linkUrl.isEmpty() && !parallelProcessor.getLinksVisited().containsKey(linkUrl)
                        && parallelProcessor.getLinksVisited().size()<MAX_URLS) {
                    parallelProcessor.getLinksVisited().put(linkUrl, BigInteger.ONE);
                    linksToProcess.add(new ParallelLinks(linkUrl, parallelProcessor));
                    LOGGER.info("Crawling url: " + linkUrl + " visits " +
                            parallelProcessor.getLinksVisited().get(linkUrl));

                } else if (!linkUrl.isEmpty() && parallelProcessor.getLinksVisited().get(linkUrl)!=null) {
                    BigInteger value = parallelProcessor.getLinksVisited().get(linkUrl).add(BigInteger.ONE);
                    parallelProcessor.getLinksVisited().put(linkUrl, value);
                    LOGGER.info("Crawling url: " + linkUrl + " visits " +
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
            LOGGER.log(Level.SEVERE,"Error get Links "+e.getMessage());
        }
        return null;
    }
}
