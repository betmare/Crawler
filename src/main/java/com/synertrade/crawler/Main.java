package com.synertrade.crawler;

import com.synertrade.crawler.html.HtmlProcessor;

import java.util.Scanner;

public class Main {
    private static final String URL = "https://en.wikipedia.org/wiki/Europe";

    public static void main(String args[]) {

        System.out.println ("Please, introduce an url to crawler (Press enter for: https://en.wikipedia.org/wiki/Europe):");
        Scanner scanner = new Scanner (System.in);
        String input = scanner.nextLine();
        if(!input.isEmpty()) {

        } else {
            new HtmlProcessor(URL).start();
        }
    }
}
