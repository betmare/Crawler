package com.synertrade.crawler.html;

import java.util.ArrayList;

import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author ernes
 */
public class HtmlProcessor {

    public List<String> getAllLinks(String url) throws Exception {
        Document htmlFile;
        List<String> listLinks = new ArrayList<>();
        htmlFile = Jsoup.connect(url).get();
        Elements links = htmlFile.getElementsByTag("a");
        for (Element link : links) {
            if (link.attr("href").contains("/wiki")) {
                listLinks.add("https://www.mediawiki.org"+link.attr("href"));
            }
        }
        return listLinks;
    }
}
