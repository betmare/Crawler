package com.synertrade.crawler;

import com.synertrade.crawler.html.HtmlProcessor;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    private final Map<String, Integer> enlacesVisitados = new LinkedHashMap<>();
    private final HtmlProcessor processor;

    public Main() {
        this.processor = new HtmlProcessor();
    }

    public void procesarEnlace(String enlace) {
        List<String> listLinks;
        try {
            listLinks = processor.getAllLinks(enlace);
            listLinks.forEach((lnk) -> {
                if (enlacesVisitados.containsKey(lnk.toLowerCase())) {
                    int contadorVisitas = enlacesVisitados.get(lnk.toLowerCase()) + 1;
                    enlacesVisitados.put(lnk, contadorVisitas);
                } else {
                    enlacesVisitados.put(lnk, 1);
                    procesarEnlace(lnk);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void imprimirMapa() {
        Set<String> llaves = enlacesVisitados.keySet();
        for (String llave : llaves) {
            System.out.println(llave +" -> "+enlacesVisitados.get(llave));
        }
    }

    public static void main(String args[]) {
        Main procesador = new Main();
        procesador.procesarEnlace("https://www.mediawiki.org/wiki/Manual:Parameters_to_index.php");
        procesador.imprimirMapa();

    }
}
