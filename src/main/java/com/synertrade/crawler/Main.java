package com.synertrade.crawler;

import com.synertrade.crawler.html.HtmlProcessor;

import java.util.Scanner;

public class Main {
    private static final String URL = "https://en.wikipedia.org/wiki/Europe";

    public static void main(String args[]) {

        System.out.println ("Please, introduce an url to crawl (Press enter for: "+URL+"):");
        Scanner scanner = new Scanner (System.in);
        String input = scanner.nextLine();
        if(!input.isEmpty()) {
            new HtmlProcessor(input).start();
        } else {
            new HtmlProcessor(URL).start();
        }
    }
}
