package com.synertrade.crawler.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileWriter;
import java.io.StringWriter;

public class MapToXml {
    private CounterVisits counterVisits;

    public MapToXml(CounterVisits counterVisits) {
        this.counterVisits = counterVisits;
    }
    public void generateXml() {
        try {
            JAXBContext jc = JAXBContext.newInstance(CounterVisits.class);
            Marshaller marshaller = jc.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(counterVisits, System.out);
            marshaller.marshal(counterVisits, writer);
            String result = writer.toString();
            FileWriter fileWriter = new FileWriter("counter_visits.xml");
            fileWriter.write(result);
            fileWriter.close();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
