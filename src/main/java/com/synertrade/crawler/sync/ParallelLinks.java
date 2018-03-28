package com.synertrade.crawler.sync;

import com.synertrade.crawler.html.HtmlProcessor;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ParallelLinks extends RecursiveAction {

    private String url;
    private HtmlProcessor htmlProcessor;

    public ParallelLinks(String url, HtmlProcessor htmlProcessor) {
        this.htmlProcessor = htmlProcessor;
        this.url = url;
    }
    @Override
    protected void compute() {
        if(!htmlProcessor.getLinksVisited().containsKey(url)) {
            invokeAll(createSubTasks());
        } else {
            htmlProcessor.getLinksVisited().get(url).add(BigInteger.ONE);
            System.out.println("Crawling url: "+url+" visits "+
                    htmlProcessor.getLinksVisited().get(url));
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
                        !htmlProcessor.getLinksVisited().containsKey(linkUrl)) {
                    htmlProcessor.getLinksVisited().put(url, BigInteger.ONE);
                    linksToProcess.add(new ParallelLinks(linkUrl, htmlProcessor));
                    System.out.println("Crawling url: " + url + " visits " +
                            htmlProcessor.getLinksVisited().get(url));
                } else if (!linkUrl.isEmpty()) {
                    htmlProcessor.getLinksVisited().get(linkUrl).add(BigInteger.ONE);
                    System.out.println("Crawling url: " + url + " visits " +
                            htmlProcessor.getLinksVisited().get(url));

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
