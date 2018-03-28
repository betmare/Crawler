package com.synertrade.crawler;

import com.synertrade.crawler.html.HtmlProcessor;

public class Main {
    private static final String URL = "https://en.wikipedia.org/wiki/Europe";

    public static void main(String args[]) {
        new HtmlProcessor(URL).start();

    }
}
